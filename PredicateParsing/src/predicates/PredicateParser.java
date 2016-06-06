package predicates;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Stack;
import java.util.function.Function;

import expressions.ComparisonExpression;
import expressions.ComparisonExpression.ComparisonOperator;
import expressions.CompoundExpression;
import expressions.CompoundExpression.CompoundOperator;
import expressions.ConstantExpression;
import expressions.ExpressionBase;
import expressions.FalseExpression;
import expressions.FunctionExpression;
import expressions.GroupedExpression;
import expressions.InModifierExpression;
import expressions.InModifierExpression.InModifierOperator;
import expressions.IndexExpression;
import expressions.IndexExpression.IndexOperation;
import expressions.KeyPathExpression;
import expressions.NilExpression;
import expressions.NotExpression;
import expressions.TrueExpression;
import expressions.helpers.FunctionExpressionHelper;

public class PredicateParser 
{
	private Stack<ExpressionBase> operatorStack = new Stack<>();
	private Stack<ExpressionBase> operandStack = new Stack<>();
	
	public ExpressionBase parse(String predicate)
	{
		for(int i = 0; i < predicate.length(); i++) 
		{
			int startIndex = i;
			char c = predicate.charAt(i);
			if(c == ' ')
				continue;
			if (Character.isLetter(c)) 
			{
				int endIndex = readUntilCondition(i, predicate, (ch) -> 
				{
					return (!Character.isAlphabetic(ch)); 
				});
				String result = predicate.substring(startIndex, endIndex);
				//Check for FUNCTION.  It requires special handling
				if("FUNCTION".equalsIgnoreCase(result))
				{
					//Keep reading until we reach the closing parens.
					int[] numberOfOpenParens = new int[]{0};
					endIndex = readUntilCondition(endIndex - 1, predicate, (ch) ->
					{
						if(ch == '(')
							numberOfOpenParens[0]++;
						else if(ch == ')')
							numberOfOpenParens[0]--;
						
						return (ch != ')' && numberOfOpenParens[0] == 0);
					});
					result = predicate.substring(startIndex, endIndex);
					handleFunction(result);
				}
				else if(isReservedKeyword(result))//Check for reserved keyword.
				{
					handleReservedKeyword(result);
				}
				else
				{
					handleKeyPath(result);
				}
				i = --endIndex;
			}
			else if(c == '[')
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) ->
				{
					return ch == ']';
				});
				String result = predicate.substring(startIndex + 1, endIndex);
				handleIndex(result);
				i = endIndex;
			}
			else if(Character.isDigit(c) || c == '-') 
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) -> 
				{
					return (!Character.isDigit(ch)) && ch != '.';
				});
				String result = predicate.substring(startIndex, endIndex);
				handleConstantDigit(result);
				i = --endIndex;
			}
			else if(c == '(')
			{
				handleGroupedExpression();
			}
			else if(c == ')')
			{
				while(!(operatorStack.peek() instanceof GroupedExpression))
				{
					ExpressionBase operator = operatorStack.pop();
					if(operator instanceof InModifierExpression || operator instanceof NotExpression)
					{
						ExpressionBase single = operandStack.pop();
						operator.setLhsExpression(single);
					}
					else
					{
						ExpressionBase rhs = operandStack.pop();
						ExpressionBase lhs = operandStack.pop();
						operator.setLhsExpression(lhs);
						operator.setRhsExpression(rhs);				
					}
					operandStack.push(operator);
				}
				operatorStack.pop();
			}
			else if(c == '{')
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) -> 
				{
					return ch == '}';
				});
				String result = predicate.substring(startIndex + 1, endIndex);
				//Split the string on commas.
				String[] split = result.split(",");
				Object[] constant = null;
				for (int j = 0; j < split.length; j++)
				{
					String string = split[j].trim();
					if(string.startsWith("'") || string.startsWith("\""))
					{
						if(constant == null)
						{
							constant = new String[split.length];
						}
						constant[j] = string.replace("'", "").replace("\"", "");
					}
					else
					{
						if(constant == null)
						{
							constant = new Number[split.length];
						}
						Number num = NumberFormat.getInstance().parse(string, new ParsePosition(0));
						constant[j] = num;
					}
				}
				handleConstant(constant);
				i = endIndex;
			}
			else if(c == '"' || c == '\'') 
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) -> 
				{
					return (ch == '"' || ch == '\'');
				});
				String result = predicate.substring(startIndex + 1, endIndex);
				handleConstant(result);
				i = endIndex;
			} 
			else if(c == '>' || c == '<' || c == '=' || c == '!') 
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) -> 
				{
					return (ch != '=');
				});
				String result = predicate.substring(startIndex, endIndex);
				handleComparisonOperator(result);
				i = --endIndex;
			}
			else if(c == '&' || c == '|')
			{
				int endIndex = readUntilCondition(startIndex, predicate, (ch) -> 
				{
					return (ch == '&' || ch == '|');
				});
				String result = predicate.substring(startIndex, endIndex + 1);
				handleCompoundOperator(result);
				i = endIndex;
			}
		}
		
		while(!operatorStack.isEmpty())
		{
			ExpressionBase operator = operatorStack.pop();
			if(operator instanceof InModifierExpression || operator instanceof NotExpression)
			{
				ExpressionBase single = operandStack.pop();
				operator.setLhsExpression(single);
			}
			else
			{
				ExpressionBase rhs = operandStack.pop();
				ExpressionBase lhs = operandStack.pop();
				operator.setLhsExpression(lhs);
				operator.setRhsExpression(rhs);				
			}
			operandStack.push(operator);
		}
		
		return operandStack.pop();
	}
	
	private void handleKeyPath(String keyPath)
	{
		KeyPathExpression expression = new KeyPathExpression(keyPath);
		operandStack.push(expression);
	}
	
	private void handleIndex(String index)
	{
		IndexOperation operation = IndexOperation.get(index);
		ExpressionBase exp = operandStack.pop();
		IndexExpression indexExpression = new IndexExpression(operation, exp);
		operandStack.push(indexExpression);
	}
	
	private void handleConstantDigit(String digit)
	{
		Number num = NumberFormat.getInstance().parse(digit, new ParsePosition(0));
		ConstantExpression expression = new ConstantExpression(num);
		operandStack.push(expression);
	}
	
	private void handleConstant(Object constant)
	{
		ConstantExpression expression = new ConstantExpression(constant);
		operandStack.push(expression);
	}
	
	private void handleFunction(String functionString)
	{
		FunctionExpression expression = FunctionExpressionHelper.createFromString(functionString);
		operandStack.push(expression);
	}
	
	private void handleGroupedExpression()
	{
		GroupedExpression exp = new GroupedExpression();
		operatorStack.push(exp);
	}
	
	private void handleReservedKeyword(String keyword)
	{
		if("TRUEPREDICATE".equalsIgnoreCase(keyword))
		{
			operandStack.push(new TrueExpression());
		}
		else if("FALSEPREDICATE".equalsIgnoreCase(keyword))
		{
			operandStack.push(new FalseExpression());
		}
		else if("AND".equalsIgnoreCase(keyword) || "OR".equalsIgnoreCase(keyword))
		{
			handleCompoundOperator(keyword);
		}
		else if("IN".equalsIgnoreCase(keyword) || "BETWEEN".equalsIgnoreCase(keyword) || "BEGINSWITH".equalsIgnoreCase(keyword))
		{
			handleComparisonOperator(keyword);
		}
		else if("ANY".equalsIgnoreCase(keyword) || "ALL".equalsIgnoreCase(keyword) || "SOME".equalsIgnoreCase(keyword) || "NONE".equalsIgnoreCase(keyword))
		{
			handleInModifierOperator(keyword);
		}
		else if("NOT".equalsIgnoreCase(keyword))
		{
			handleNotOperator();
		}
		else if("NIL".equalsIgnoreCase(keyword))
		{
			handleNil();
		}
	}
	
	private void handleNil()
	{
		NilExpression expression = new NilExpression();
		operandStack.push(expression);
	}
	
	private void handleNotOperator()
	{
		NotExpression expression = new NotExpression();
		checkOperatorStack(expression);
	}
	
	private void handleComparisonOperator(String opString)
	{
		ComparisonOperator operator = ComparisonOperator.get(opString);
		ComparisonExpression comparisonExpression = new ComparisonExpression(operator);
		checkOperatorStack(comparisonExpression);
	}
	
	private void handleCompoundOperator(String opString)
	{
		CompoundOperator operator = CompoundOperator.get(opString);
		CompoundExpression compoundExpression = new CompoundExpression(operator);
		checkOperatorStack(compoundExpression);
	}
	
	private void handleInModifierOperator(String opString)
	{
		InModifierOperator operator = InModifierOperator.get(opString);
		InModifierExpression expression = new InModifierExpression(operator);
		checkOperatorStack(expression);
	}
	
	private void checkOperatorStack(ExpressionBase expressionToAdd)
	{
		if(operatorStack.isEmpty())
		{
			operatorStack.push(expressionToAdd);
		}
		else
		{
			//Check for higher priority operator
			ExpressionBase expression = operatorStack.peek();
			if(expression instanceof ComparisonExpression)
			{
				ExpressionBase rhs = operandStack.pop();
				ExpressionBase lhs = operandStack.pop();
				ExpressionBase operatorExpression = operatorStack.pop();
				operatorExpression.setLhsExpression(lhs);
				operatorExpression.setRhsExpression(rhs);
				operandStack.push(operatorExpression);
			}
			operatorStack.push(expressionToAdd);
		}
	}
	
	private int readUntilCondition(int startAt, String stringToRead, Function<Character, Boolean> condition) 
	{
		int endIndex = startAt;
		while(true) 
		{
			if(++startAt == stringToRead.length())
			{
				//If adding one to this would cause it to be the same as the length, just return
				return startAt;
			}
			char c = stringToRead.charAt(startAt);
			if(condition.apply(c)) 
			{
				endIndex = startAt;
				break;
			}
		}
		
		return endIndex;
	}
	
	private boolean isReservedKeyword(String word)
	{
		return "AND".equalsIgnoreCase(word) ||
				"OR".equalsIgnoreCase(word) ||
				"IN".equalsIgnoreCase(word) ||
				"ANY".equalsIgnoreCase(word) ||
				"ALL".equalsIgnoreCase(word) ||
				"SOME".equalsIgnoreCase(word) ||
				"NONE".equalsIgnoreCase(word) ||
				"NOT".equalsIgnoreCase(word) ||
				"NIL".equalsIgnoreCase(word) ||
				"BETWEEN".equalsIgnoreCase(word) ||
				"BEGINSWITH".equalsIgnoreCase(word) ||
				"TRUEPREDICATE".equalsIgnoreCase(word) ||
				"FALSEPREDICATE".equalsIgnoreCase(word);
	}
}
