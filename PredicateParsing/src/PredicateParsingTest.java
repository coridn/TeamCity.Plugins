import predicates.Predicate;

public class PredicateParsingTest 
{
	public static void main(String[] args) 
	{
		String predicate = "CurrentWeight < 6 AND InstrumentType = \"Common.InstrumentType.TransmitterOnly\" AND CommunicationProtocol IN {\"Common.CommunicationProtocol.4-20mAHART\", \"Common.CommunicationProtocol.4-20mAHARTWithTHUM\"} AND FlowOutput = \"Common.FlowOutput.ProcessVariablesOnly\" AND MeasurementType[SIZE] > 1 AND \"Common.MeasurementType.DifferentialPressure\" IN MeasurementType";
		
		predicate = "TankType = \"TankGauging.TankType.FixedRoofTank\" AND LiquidProductType = 1 AND ProcessTemperatureMinimum >= -40 AND ((TankSizeUnit = 4 AND TankHeight <= 100) OR (TankSizeUnit = 3 AND TankHeight <= 30)) AND ((ProcessPressureMaximumUnit = 22 AND (FUNCTION(ProcessPressureMinimum, 'convertPressureToBar:', 40) > 2)) OR (ProcessPressureMaximumUnit = 40 AND ProcessPressureMaximum > 29)) AND ApplicationType = 'TankGauging.ApplicationType.InventoryControl'";
		
		Predicate pred = Predicate.predicateWithString(predicate);
		
		FilterModel model = new FilterModel();
		model.setObjectForKey("TankType", "TankGauging.TankType.FixedRoofTank");
		model.setObjectForKey("LiquidProductType", 1);
		model.setObjectForKey("ProcessTemperatureMinimum", 0);
		model.setObjectForKey("TankSizeUnit", 3);
		model.setObjectForKey("TankHeight", 20);
		model.setObjectForKey("ProcessPressureMaximumUnit", 40);
		model.setObjectForKey("ProcessPressureMinimum", 4);
		model.setObjectForKey("ProcessPressureMaximum", 30);
		model.setObjectForKey("ApplicationType", "TankGauging.ApplicationType.InventoryControl");
		
		boolean isTrue = pred.evaluateWithObject(model);
		
		assert(isTrue);
	}
}
