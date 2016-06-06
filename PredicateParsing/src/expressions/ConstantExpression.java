package expressions;

import keyvaluecoding.IKVCObject;

public class ConstantExpression extends ExpressionBase 
{
	private final Object constantValue;
	
	public static ConstantExpression create(Object value) 
	{
		return new ConstantExpression(value);
	}
	
	public ConstantExpression(Object value) 
	{
		this.constantValue = value;
	}

	@Override
	public Object getValueWithObject(IKVCObject object) 
	{
		return this.constantValue;
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression) 
	{
		throw new UnsupportedOperationException("Not implemented in this class");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression) 
	{
		throw new UnsupportedOperationException("Not implemented in this class");
	}

	@Override
	public ExpressionBase getLhsExpression()
	{
		throw new UnsupportedOperationException("Not implemented in this class");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Not implemented in this class");
	}
}
