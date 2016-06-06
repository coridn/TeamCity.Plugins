package expressions;

import keyvaluecoding.IKVCObject;

public class KeyPathExpression extends ExpressionBase 
{
	private final String keyPath;
	
	public KeyPathExpression(String keyPath) 
	{
		this.keyPath = keyPath;
	}

	@Override
	public Object getValueWithObject(IKVCObject object) 
	{
		return object.valueForKey(this.keyPath);
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
