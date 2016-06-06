package expressions;

import keyvaluecoding.IKVCObject;

public class NilExpression extends ExpressionBase
{
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return null;
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		throw new UnsupportedOperationException("Never implement.");
	}

	@Override
	public ExpressionBase getLhsExpression()
	{
		throw new UnsupportedOperationException("Never implement.");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Never implement.");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Never implement.");
	}

}
