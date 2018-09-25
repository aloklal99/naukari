package alok.ranger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.security.access.Permission;
import org.apache.hadoop.hbase.security.access.Permission.Action;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.ranger.plugin.policyengine.RangerAccessRequest;
import org.aspectj.lang.JoinPoint.StaticPart;

public aspect HbaseRangerPluginAspect {
	private static final Log LOG = LogFactory.getLog("org.apache.ranger.authorization.hbase.HbaseAuthUtilsAspect");
	
	pointcut everyMethod() : 
//		execution (* AspectUtils+.*(..)) ||
//		execution (* HbaseAccessConfig.*(..)) ||
//		execution (* HbaseAccessRequest+.*(..)) ||
//		execution (* HbaseAuthUtils+.*(..)) ||
		execution (* AuthorizationSession+.*(..)) ||
		execution (* RangerAuthorizationCoprocessor.*(..))
//		execution (* HbaseFactory+.*(..))
		;
	
	before() : everyMethod() {
		log(thisJoinPointStaticPart, "Entering");
	}
	
	after() returning : everyMethod() && !(execution (* AuthorizationSession.authorize(..))) {
		log(thisJoinPointStaticPart, "Exiting");
	}
	
	after() throwing : everyMethod()  && !(execution (* AuthorizationSession.authorize(..))) {
		log(thisJoinPointStaticPart, "Throwing");
	}
	
	after() returning (User user) : execution(User RangerAuthorizationCoprocessor+.getActiveUser(..)) {
		if (LOG.isDebugEnabled()) {
			String message = String.format("\tgetActiveUser: returning [%s].", user.getShortName());
			LOG.debug(message);
		}
	}
	
	after(User user) returning (boolean result) : execution (boolean RangerAuthorizationCoprocessor+.isSuperUser(User)) && args(user) {
		if (LOG.isDebugEnabled()) {
			String message = String.format("\tisSuperUser: User [%s] is%s super user.", user.getShortName(), result ? "": " NOT");
			LOG.debug(message);
		}
	}
	
	after() returning (boolean result) : execution (boolean RangerAuthorizationCoprocessor+.isPermissionGranted(..)) {
		if (LOG.isDebugEnabled()) {
			String message = String.format("\tisPermissionGranted: %s.", result ? "yes": "no");
			LOG.debug(message);
		}
	}
	
	after (String tableName) returning (boolean result) : execution (boolean RangerAuthorizationCoprocessor+.isSpecialTable(String)) && args(tableName) {
		if (LOG.isDebugEnabled()) {
			String message = String.format("\tisSpecialTable: Table [%s] is%s a special table.", tableName, result ? "": " NOT");
			LOG.debug(message);
		}
	}
	
	AuthorizationSession around ()  :
		execution(AuthorizationSession AuthorizationSession+.authorize()) {
		
		AuthorizationSession session = (AuthorizationSession) thisJoinPoint.getThis();
		if (LOG.isDebugEnabled()) {
			String message = String.format("\tAuthorizing Request: %s", session.requestToString());
			LOG.debug(message);
		}
		try {
			return proceed();
		} finally {
			String message = String.format("\tResult: %s", session._result.toString());
			LOG.debug(message);
			
		}
	}
	
	void logTableAccess(String request, String type, String value, Permission.Action actionEnum, boolean allowed) {
		String action = actionEnum.toString().toLowerCase();
		String verdict = allowed ? "allowed" : "denied";
		
		if (LOG.isDebugEnabled()) {
			String message = String.format("\trequirePermission: request [%s] on %s[%s] requiring access [%s] was %s!", request, type, value, action, verdict);
			LOG.debug(message);
		}
	}
	
	void log(StaticPart joinPoint, String action) {
		String fullMethodName = joinPoint.getSignature().getName();
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
		
		if (LOG.isDebugEnabled()) {
			String message = String.format("%s.%s: %s...", className, fullMethodName, action);
			LOG.debug(message);
		}
	}
}
