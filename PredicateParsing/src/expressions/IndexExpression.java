package expressions;

import java.text.NumberFormat;
import java.text.ParsePosition;

import keyvaluecoding.IKVCObject;

public class IndexExpression extends ExpressionBase
{
	private final IndexOperation indexOperation;
	private final ExpressionBase expression;
	
	public IndexExpression(IndexOperation operation, ExpressionBase expression)
	{
		this.expression = expression;
		this.indexOperation = operation;
	}
	
	@Override
	public Object getValueWithObject(IKVCObject object)
	{
		return this.indexOperation.evaluate(expression, object);
	}

	@Override
	public void setLhsExpression(ExpressionBase lhsExpression)
	{
		throw new UnsupportedOperationException("Never implemented.");
	}

	@Override
	public void setRhsExpression(ExpressionBase rhsExpression)
	{
		throw new UnsupportedOperationException("Never implemented.");
	}
	
	@Override
	public ExpressionBase getLhsExpression()
	{
		throw new UnsupportedOperationException("Never implemented.");
	}

	@Override
	public ExpressionBase getRhsExpression()
	{
		throw new UnsupportedOperationException("Never implemented.");
	}

	public static class IndexOperation
	{
		public static final IndexOperation Size = new IndexOperation(-3);
		public static final IndexOperation First = new IndexOperation(-2);
		public static final IndexOperation Last = new IndexOperation(-1);
		
		private final int index;
		
		public IndexOperation(int value)
		{
			this.index = value;
		}
		
		public static IndexOperation get(String opString)
		{
			if("SIZE".equalsIgnoreCase(opString))
			{
				return IndexOperation.Size;
			}
			else if("FIRST".equalsIgnoreCase(opString))
			{
				return IndexOperation.First;
			}
			else if("LAST".equalsIgnoreCase(opString))
			{
				return IndexOperation.Last;
			}
			else
			{
				ParsePosition p = new ParsePosition(0);
				Number num = NumberFormat.getInstance().parse(opString, p);
				if(p.getErrorIndex() != -1)
				{
					return new IndexOperation(num.intValue());
				}
				else
				{
					throw new IllegalArgumentException(opString);
				}
			}
		}
		
		public Object evaluate(ExpressionBase expression, IKVCObject object)
		{
			Object[] array = (Object[])expression.getValueWithObject(object);
			if(array == null)
			{
				return null;
			}
			if(this == IndexOperation.Size)
			{
				return array.length;
			}
			else if(this == IndexOperation.First)
			{
				return array[0];
			}
			else if(this == IndexOperation.Last)
			{
				return array[array.length - 1];
			}
			else
			{
				return array[this.index];
			}
		}
	}
}
