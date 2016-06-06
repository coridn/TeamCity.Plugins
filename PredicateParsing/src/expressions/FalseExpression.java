package expressions;

import keyvaluecoding.IKVCObject;

public class FalseExpression extends ExpressionBase
{
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return false;
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		throw new UnsupportedOperationException("Not Implemented.");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Not Implemented.");
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
