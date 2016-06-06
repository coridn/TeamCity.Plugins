package expressions;

import keyvaluecoding.IKVCObject;

public class CompoundExpression extends ExpressionBase
{
	private final CompoundOperator operator;
	private ExpressionBase lhsExpression;
	private ExpressionBase rhsExpression;
	
	public CompoundExpression(CompoundOperator operator)
	{
		this.operator = operator;
	}
	
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return this.operator.evaluate(lhsExpression, rhsExpression, object);
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		this.lhsExpression = lhsExpression;
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		this.rhsExpression = rhsExpression;
	}
	
	@Override
	public ExpressionBase getLhsExpression()
	{
		return this.lhsExpression;
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		return this.rhsExpression;
	}
	
	public enum CompoundOperator
	{
		Unknown,
		And,
		Or;
		
		public static CompoundOperator get(String compoundOperator)
		{
			if("AND".equalsIgnoreCase(compoundOperator) || "&&".equals(compoundOperator))
			{
				return CompoundOperator.And;
			}
			else if("OR".equalsIgnoreCase(compoundOperator) || "||".equals(compoundOperator))
			{
				return CompoundOperator.Or;
			}
			return CompoundOperator.Unknown;
		}
		
		public boolean evaluate(ExpressionBase lhsExpression, ExpressionBase rhsExpression, IKVCObject object)
		{
			Boolean lhsResult = (Boolean)lhsExpression.getValueWithObject(object);
			if(this == CompoundOperator.And && !lhsResult)
				return false;
			Boolean rhsResult = (Boolean)rhsExpression.getValueWithObject(object);
			
			if(this == And)
			{
				return lhsResult && rhsResult;
			}
			else if(this == Or)
			{
				return lhsResult || rhsResult;
			}
			throw new UnsupportedOperationException("Unhandled Compound Operator");
		}
	}
}
