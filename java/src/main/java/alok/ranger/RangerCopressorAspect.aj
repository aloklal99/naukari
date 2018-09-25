package alok.ranger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.coprocessor.MasterCoprocessorEnvironment;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos.SnapshotDescription;
import org.aspectj.lang.JoinPoint.StaticPart;

import java.io.IOException;


public aspect RangerCopressorAspect {

	private static final Log LOG = LogFactory.getLog("org.apache.ranger.authorization.hbase.RangerCopressorAspect");
	HbaseFactory _factory = HbaseFactory.getInstance();
	HbaseAuthUtils _authUtils = _factory.getAuthUtils();
	AspectUtils _aspectUtils = _factory.getAspectUtils();
	final String className = "RangerCoprocessor";
	
	// ### Namespace - start
	pointcut createModifyNamespace(): 
		target(RangerAuthorizationCoprocessor+) && 
		execution(void pre*(ObserverContext<MasterCoprocessorEnvironment>, NamespaceDescriptor));
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, NamespaceDescriptor ns) returning :
		createModifyNamespace() &&
		args(ctx, ns) {
		
		String namespace = _authUtils.getNameSpace(ns);
		logGrant(thisJoinPointStaticPart, namespace);
	}
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, NamespaceDescriptor ns) throwing (IOException e) :
		createModifyNamespace() &&
		args(ctx, ns) {

		String namespace = _authUtils.getNameSpace(ns);
		logDenial(thisJoinPointStaticPart, namespace);
	}
	
	pointcut deleteNamespace(): 
		target(RangerAuthorizationCoprocessor+) && 
		execution(void pre*(ObserverContext<MasterCoprocessorEnvironment>, String));
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, String namespace) returning :
		deleteNamespace() &&
		args(ctx, namespace) {
		
		logGrant(thisJoinPointStaticPart, namespace);
	}
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, String namespace) throwing (IOException e) :
		deleteNamespace() &&
		args(ctx, namespace) {

		logDenial(thisJoinPointStaticPart, namespace);
	}
	// ### Namespace - end
	
	// ### snapshot - start
	pointcut snapshotMethods(): 
		target(RangerAuthorizationCoprocessor+) && 
		execution (void pre*(ObserverContext<MasterCoprocessorEnvironment>, SnapshotDescription, HTableDescriptor));
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, SnapshotDescription snapshot, HTableDescriptor tableDescriptor) returning :
		snapshotMethods() &&
		args(ctx, snapshot, tableDescriptor) {
		
		String tableName = _authUtils.getTable(tableDescriptor);
		logGrant(thisJoinPointStaticPart, tableName);
	}
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, SnapshotDescription snapshot, HTableDescriptor tableDescriptor) throwing :
		snapshotMethods() &&
		args(ctx, snapshot, tableDescriptor) {

		String tableName = _authUtils.getTable(tableDescriptor);
		logDenial(thisJoinPointStaticPart, tableName);
	}

	pointcut snapshotDelete(): 
		target(RangerAuthorizationCoprocessor+) && 
		execution (void pre*(ObserverContext<MasterCoprocessorEnvironment>, SnapshotDescription));
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, SnapshotDescription snapshot) returning :
		snapshotDelete() &&
		args(ctx, snapshot) {
		
		logGrant(thisJoinPointStaticPart, "");
	}
	
	after (ObserverContext<MasterCoprocessorEnvironment> ctx, SnapshotDescription snapshot) throwing :
		snapshotDelete() &&
		args(ctx, snapshot) {

		logDenial(thisJoinPointStaticPart, "");
	}
	// ### snapshot - end
	
	// ### table - start
	pointcut table1():
		target(RangerAuthorizationCoprocessor+) &&
		execution (void pre*(ObserverContext<MasterCoprocessorEnvironment>, HTableDescriptor, HRegionInfo[]));
	
	after(ObserverContext<MasterCoprocessorEnvironment> ctx, HTableDescriptor tableDescriptor, HRegionInfo[] regions) returning :
		table1() &&
		args(ctx, tableDescriptor, regions) {
		
		String tableName = _authUtils.getTable(tableDescriptor);
		logGrant(thisJoinPointStaticPart, tableName);
	}
	
	after(ObserverContext<MasterCoprocessorEnvironment> ctx, HTableDescriptor tableDescriptor, HRegionInfo[] regions) throwing :
		table1() &&
		args(ctx, tableDescriptor, regions) {
		
		String tableName = _authUtils.getTable(tableDescriptor);
		logDenial(thisJoinPointStaticPart, tableName);
	}
	
	pointcut table2():
		target(RangerAuthorizationCoprocessor+) &&
		execution (void pre*(ObserverContext<MasterCoprocessorEnvironment>, TableName));

	after(ObserverContext<MasterCoprocessorEnvironment> ctx, TableName tablename) returning :
		table2() &&
		args(ctx, tablename) {
		
		String tableName = _authUtils.getTable(tablename);
		logDenial(thisJoinPointStaticPart, tableName);
	}

	after(ObserverContext<MasterCoprocessorEnvironment> ctx, TableName tablename) throwing :
		table2() &&
		args(ctx, tablename) {

		String tableName = _authUtils.getTable(tablename);
		logDenial(thisJoinPointStaticPart, tableName);
	}
	// ### table - end
	
	// utility methods
	void logDenial(StaticPart joinPoint, HTableDescriptor tableDescriptor) {
		String table= _authUtils.getTable(tableDescriptor);
		logDenial(joinPoint, table);
	}
	
	void logDenial(StaticPart joinPoint, String target) {
		logOperation(joinPoint, target, false);
	}

	void logGrant(StaticPart joinPoint, String target) {
		logOperation(joinPoint, target, true);
	}
	
	void logOperation(StaticPart joinPoint, String target, boolean allowed) {
		String fullMethodName = joinPoint.getSignature().getName();
		String operation = _aspectUtils.getOperation(fullMethodName);
		String targetType = _aspectUtils.getTargetType(fullMethodName);

		if (LOG.isDebugEnabled()) {
			String message = String.format("\tAttempt to %s resource %s(%s) %s.", operation, targetType, target, allowed ? "allowed": "denied");
			LOG.debug(message);
		}
	}
	
	void logMethodEntry(StaticPart joinPoint) {

		String fullMethodName = joinPoint.getSignature().getName();
		if (LOG.isDebugEnabled()) {
			String message = String.format("%s.%s: Entering...", className, fullMethodName);
			LOG.debug(message);
		}
	}

	void logMethodExit(StaticPart joinPoint) {

		String fullMethodName = joinPoint.getSignature().getName();
		if (LOG.isDebugEnabled()) {
			String message = String.format("%s.%s: Exiting...", className, fullMethodName);
			LOG.debug(message);
		}
	}
}
