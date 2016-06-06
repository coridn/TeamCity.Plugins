package expressions;

import keyvaluecoding.IKVCObject;

/**
 * This is a dummy class that's just meant to represent the start of a group. It has no logic involved.
 * @author coridn
 *
 */
public class GroupedExpression extends ExpressionBase
{
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		throw new UnsupportedOperationException("This should never happen.");
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		throw new UnsupportedOperationException("This should never happen.");
	}

	@Override
	public ExpressionBase getLhsExpression()
	{
		throw new UnsupportedOperationException("This should never happen.");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("This should never happen.");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("This should never happen.");
	}

}
