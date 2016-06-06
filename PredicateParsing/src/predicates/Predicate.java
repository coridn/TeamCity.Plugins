package predicates;

import expressions.ExpressionBase;
import keyvaluecoding.IKVCObject;

public class Predicate 
{
	private ExpressionBase rootExpression;
	
	public static Predicate predicateWithString(String predicateString) 
	{
		return new Predicate(predicateString);
	}
	
	public Predicate(String predicateString) 
	{
		PredicateParser parser = new PredicateParser();
		this.rootExpression = parser.parse(predicateString);
	}
	
	public boolean evaluateWithObject(IKVCObject kvcObject) 
	{
		return (Boolean)this.rootExpression.getValueWithObject(kvcObject);
	}
}
