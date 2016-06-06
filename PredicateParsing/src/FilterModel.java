import java.util.HashMap;
import java.util.Map;

import keyvaluecoding.IKVCObject;

public class FilterModel implements IKVCObject 
{
	private Map<String, Object> _selections;
	
	public FilterModel() 
	{
		this._selections = new HashMap<>(20);
	}
	
	public void setObjectForKey(String key, Object value) 
	{
		this._selections.put(key, value);
	}

	@Override
	public Object valueForKey(String key) 
	{
		Object value = this._selections.get(key);
		if(value != null) 
		{
			return value;
		}
		return null;
	}

}
