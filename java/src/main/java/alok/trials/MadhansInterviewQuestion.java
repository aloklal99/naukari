package alok.trials;

import java.util.ArrayList;
import java.util.List;

public class MadhansInterviewQuestion {

	public static void main(String[] args) {
		A a = new A(100);
		B b = new B(100);
		
		System.out.println("A.value=" + a.getValue());
		System.out.println("B.value=" + b.getValue());
		
		Listener listener = new Listener(a, b);
		a.addChangeListner(listener);
		b.addChangeListner(listener);
		
		a.setValue(101);
		System.out.println("A.value=" + a.getValue());
		System.out.println("B.value=" + b.getValue());
		b.setValue(99);
		System.out.println("A.value=" + a.getValue());
		System.out.println("B.value=" + b.getValue());
	}

	public interface ListenerA {
		void valueChangedA(int newValue);
	}
	
	public static class A {
		private int _value = 0;
		private List<ListenerA> _listeners = new ArrayList<ListenerA>();
		public A(int value) {
			_value = value;
		}
		
		int getValue() {
			return _value;
		}
		
		void addChangeListner(ListenerA listener) {
			_listeners.add(listener);
		}
		
		void setValue(int newValue) {
			_value = newValue;
			
			for (ListenerA listener : _listeners) {
				listener.valueChangedA(newValue);
			}
		}
	}

	public interface ListenerB {
		void valueChangedB(int newValue);
	}
	
	public static class B {
		private int _value = 0;
		private List<ListenerB> _listeners = new ArrayList<ListenerB>();
		public B(int value) {
			_value = value;
		}
		
		int getValue() {
			return _value;
		}
		
		void addChangeListner(ListenerB listener) {
			_listeners.add(listener);
		}
		
		void setValue(int newValue) {
			_value = newValue;
			
			for (ListenerB listener : _listeners) {
				listener.valueChangedB(newValue);
			}
		}
	}

	public static class Listener implements ListenerA, ListenerB {

		private final A _a;
		private final B _b;
		
		public Listener(A a, B b) {
			_a = a;
			_b = b;
		}

		public void valueChangedA(int newValue) {
			if (newValue != _b.getValue()) {
				_b.setValue(newValue);
			}
		}

		public void valueChangedB(int newValue) {
			if (newValue != _a.getValue()) {
				_a.setValue(newValue);
			}
		}
		
	}
}
