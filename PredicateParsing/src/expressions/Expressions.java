package expressions;

public class Expressions
{
	public ConstantExpression constant(Object constant)
	{
		return new ConstantExpression(constant);
	}
	
	public KeyPathExpression keyPath(String keypath)
	{
		return new KeyPathExpression(keypath);
	}
	
//	public ComparisonExpression andComparison()
//	{
//		
//	}
}
