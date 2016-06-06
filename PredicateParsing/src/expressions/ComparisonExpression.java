package expressions;

import java.util.Arrays;

import keyvaluecoding.IKVCObject;

public class ComparisonExpression extends ExpressionBase 
{
	private final ComparisonOperator operator;
	private ExpressionBase lhsExpression;
	private ExpressionBase rhsExpression;
	
	public ComparisonExpression(ComparisonOperator operator) 
	{
		this.operator = operator;
	}
	
	public void setLhsExpression(ExpressionBase lhsExpression) 
	{
		this.lhsExpression = lhsExpression;
	}
	
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
	
	@Override
	public Object getValueWithObject(IKVCObject object) 
	{
		assert(lhsExpression != null);
		assert(rhsExpression != null);
		return this.operator.evaluate(lhsExpression, rhsExpression, object);
	}
	
	public enum ComparisonOperator 
	{
		Unknown,
		GreaterThan,
		GreaterThanOrEqualTo,
		Equals,
		NotEquals,
		LessThan,
		LessThanOrEqualTo,
		In,
		Between,
		BeginsWith;
		
		public static ComparisonOperator get(String operatorString) 
		{
			if(">".equals(operatorString)) 
			{
				return ComparisonOperator.GreaterThan;
			} 
			else if(">=".equals(operatorString)) 
			{
				return ComparisonOperator.GreaterThanOrEqualTo;
			} 
			else if("=".equals(operatorString) || "==".equals(operatorString)) 
			{
				return ComparisonOperator.Equals;
			}
			else if("!=".equals(operatorString))
			{
				return ComparisonOperator.NotEquals;
			}
			else if("<".equals(operatorString)) 
			{
				return ComparisonOperator.LessThan;
			} 
			else if("<=".equals(operatorString)) 
			{
				return ComparisonOperator.LessThanOrEqualTo; 
			}
			else if("IN".equalsIgnoreCase(operatorString))
			{
				return ComparisonOperator.In;
			}
			else if("BETWEEN".equalsIgnoreCase(operatorString))
			{
				return ComparisonOperator.Between;
			}
			else if("BEGINSWITH".equalsIgnoreCase(operatorString))
			{
				return ComparisonOperator.BeginsWith;
			}
			return ComparisonOperator.Unknown;
		}
		
		public boolean evaluate(ExpressionBase lhs, ExpressionBase rhs, IKVCObject object) 
		{
			Object lhsValue = lhs.getValueWithObject(object);
			Object rhsValue = rhs.getValueWithObject(object);
			if(lhsValue == null || rhsValue == null)
			{
				if(this == ComparisonOperator.Equals)
				{
					return lhsValue == rhsValue;
				}
				else if(this == ComparisonOperator.NotEquals)
				{
					return lhsValue != rhsValue;
				}
			}
			if(lhsValue instanceof Number || rhsValue instanceof Number) 
			{
				Number num = null;
				Object otherValue = null;
				if(lhsValue instanceof Number)
				{
					num = ((Number)lhsValue).doubleValue();
					otherValue = rhsValue;
				}
				double numDouble = num.doubleValue();
				if(this == GreaterThan) 
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble > otherNum;
				}
				else if(this == GreaterThanOrEqualTo) 
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble >= otherNum;
				}
				else if(this == LessThan) 
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble < otherNum;
				}
				else if(this == LessThanOrEqualTo) 
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble <= otherNum;
				} 
				else if(this == Equals) 
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble == otherNum;
				}
				else if(this == NotEquals)
				{
					double otherNum = ((Number)otherValue).doubleValue();
					return numDouble != otherNum;
				}
				else if(this == Between)
				{
					Number[] values = (Number[])otherValue;
					double first = values[0].doubleValue();
					double second = values[1].doubleValue();
					return first <= numDouble && numDouble <= second;
				}
			} 
			else if(lhsValue instanceof Object[] || rhsValue instanceof Object[])
			{
				if(this == ComparisonOperator.In)
				{
					//One must be an array for the IN operator to work.
					Object[] theCollection = null;
					Object selection = null;
					if(lhsValue instanceof Object[])
					{
						theCollection = (Object[])lhsValue;
						selection = (Object)rhsValue;
					}
					else
					{
						theCollection = (Object[])rhsValue;
						selection = (Object)lhsValue;
					}
					return Arrays.asList(theCollection).contains(selection);
				}
			}
			else if(lhsValue instanceof String || rhsValue instanceof String) 
			{
				String lhsString = (String)lhsValue;
				String rhsString = (String)rhsValue;
				if(lhsString == null && rhsString == null)
				{
					if(this == ComparisonOperator.Equals)
						return true;
					else if(this == ComparisonOperator.NotEquals)
						return false;
					//begins with shouldn't be needed here since one will always be constant.
				}
				else if(lhsString == null)
				{
					if(this == ComparisonOperator.Equals)
						return rhsString.equals(lhsString);
					else if(this == ComparisonOperator.NotEquals)
						return !rhsString.equals(lhsString);
				}
				if(this == ComparisonOperator.Equals)
					return lhsString.equals(rhsString);
				else if(this == ComparisonOperator.NotEquals)
					return lhsString.equals(rhsString);
				else if(this == ComparisonOperator.BeginsWith)
					return lhsString.startsWith(rhsString);
			}
			return false;
		}
	}
}
