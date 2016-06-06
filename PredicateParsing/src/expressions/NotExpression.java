package expressions;

import keyvaluecoding.IKVCObject;

public class NotExpression extends ExpressionBase
{
	private ExpressionBase lhsExpression;
	
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return !((Boolean)this.lhsExpression.getValueWithObject(object));
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		this.lhsExpression = lhsExpression;
	}

	@Override
	public ExpressionBase getLhsExpression()
	{
		return this.lhsExpression;
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Not implemented.");
	}

}
