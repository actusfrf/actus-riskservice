package org.actus.risksrv3.core.attributes;
/**
 * A data structure representing the set of ACTUS contract attributes
 * <p>
 * This is an internal representation of the various contract attributes used when 
 * evaluating the contract pay-off function. Hence, as opposed to an external, 
 * representation, e.g. a file, database, etc., this component carries the values of
 * attributes in their respective Java-data type which is required when evaluating
 * the {@code ContractType.lifecycle} method.
 * 
 * @see <a href="http://www.projectactus.org/projectactus/?page_id=356">ACTUS Data Dictionary</a>
 */
public abstract interface ContractModelProvider {
	
    /**
     * Access a Contract Attribute in its data type
     * <p>
     * The various attributes collected in a {@code ContractModelProvider} are used as parameters 
     * when evaluating the {@code ContractType.lifecycle} method. This method allows accessing the various
     * attribute values. In order to make sure that attributes are returned in the correct target
     * data type (according to the ACTUS data dictionary) the respective class name should be
     * provided when calling the method, e.g. {@code model.<String>getAs("ContractRole")}, 
     * {@code model.<Double>getAs("NotionalPrincipal")}. If not provided explicitly, the target data
     * type is inferred from the context (if possible).
     * 
     * @param name the name of the attribute to retrieve
     * 
     * @return a type-casted object-reference to the attribute requested.
     * 
     * @throws ClassCastException
     */ 
    public <T> T getAs(String name);

}
