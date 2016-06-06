package expressions;

import java.util.ArrayList;

import keyvaluecoding.IKVCObject;

public class InModifierExpression extends ExpressionBase
{
	private final InModifierOperator operator;
	private ExpressionBase inExpression;
	
	public InModifierExpression(InModifierOperator operator)
	{
		this.operator = operator;
	}

	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return this.operator.evaluate(this.inExpression, object);
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		this.inExpression = lhsExpression;
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Never implement.");
	}
	
	@Override
	public ExpressionBase getLhsExpression()
	{
		return this.inExpression;
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Never implement.");
	}
	
	public enum InModifierOperator
	{
		Any,
		All,
		Some,
		None;
		
		public static InModifierOperator get(String opString)
		{
			if("ANY".equalsIgnoreCase(opString))
			{
				return InModifierOperator.Any;
			}
			else if("ALL".equalsIgnoreCase(opString))
			{
				return InModifierOperator.All;
			}
			else if("SOME".equalsIgnoreCase(opString))
			{
				return InModifierOperator.Any;
			}
			else if("NONE".equalsIgnoreCase(opString))
			{
				return InModifierOperator.None;
			}
			throw new IllegalArgumentException(opString);
		}
		
		public boolean evaluate(ExpressionBase inExpression, IKVCObject object)
		{
			//Get the left and write of the In Expression
			ExpressionBase lhsExpression = inExpression.getLhsExpression();
			ExpressionBase rhsExpression = inExpression.getRhsExpression();
			//Both of these should be arrays
			Object[] lhsArray = (Object[])lhsExpression.getValueWithObject(object);
			ArrayList<Object> lhsList = new ArrayList<>();
			for (Object obj : lhsArray)
			{
				lhsList.add(obj);
			}
			Object[] rhsArray = (Object[])rhsExpression.getValueWithObject(object);
			ArrayList<Object> rhsList = new ArrayList<>();
			for (Object obj : rhsArray)
			{
				rhsList.add(obj);
			}
			if(this == InModifierOperator.Any)
			{
				for (Object obj : lhsList)
				{
					if(rhsList.contains(obj))
					{
						return true;
					}
				}
			}
			else if(this == InModifierOperator.All)
			{
				for (Object obj : lhsList)
				{
					if(!rhsList.contains(obj))
					{
						return false;
					}
				}
				return true;
			}
			else
			{
				//Skipping NONE for now.
			}
			return false;
		}
	}
}
