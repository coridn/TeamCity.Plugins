package expressions;

import keyvaluecoding.IKVCObject;

public abstract class ExpressionBase
{
	public abstract Object getValueWithObject(IKVCObject object);
	public abstract void setLhsExpression(ExpressionBase lhsExpression);
	public abstract ExpressionBase getLhsExpression();
	public abstract void setRhsExpression(ExpressionBase rhsExpression);
	public abstract ExpressionBase getRhsExpression();
}
