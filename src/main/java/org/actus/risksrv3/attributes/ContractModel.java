package org.actus.risksrv3.attributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.actus.AttributeConversionException;
import org.actus.contracts.ContractType;


/**
 * A data structure representing the set of ACTUS contract attributes
 * <p>
 * This is a simple implementation of the {@link ContractModelProvider} interface representing
 * a generic data structure for the various ACTUS attributes parametrizing a {@link ContractType}.
 * Method {@code parse} allows parsing the attributes from an input {@code String} representation
 * to the internal data types.
 * <p>
 * Note, an ACTUS {@link ContractType} can deal with any data structure implementing the
 * {@link ContractModelProvider} interface. Thus, depending on the system ACTUS is embedded in,
 * more efficient data structures and parsing methods are possible.
 *
 * @see <a href="https://www.actusfrf.org/data-dictionary">ACTUS Data Dictionary</a>
 */

public class ContractModel implements ContractModelProvider{
	
    private Map<String, Object> attributes;

    /**
     * Constructor
     * <p>
     * The map provided as the constructor argument is expected to contain <key,value> pairs
     * of attributes using ACTUS attribute names (in long form) and data types of values
     * as per official ACTUS data dictionary.
     */
    public ContractModel(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * Create a new contract model from a java map
     * <p>
     * The map provided as the method argument is expected to contain <key,value> pairs
     * of attributes using ACTUS attribute names (in long form) and data types of values
     * as per official ACTUS data dictionary.
     *
     * @param attributes a java map of attributes as per ACTUS data dictionary
     * @return an instance of ContractModel containing the attributes provided with the method argument
     */
    public static ContractModel of(Map<String, Object> attributes) {
        return new ContractModel(attributes);
    }
    
    public Map<String,Object> getAttributes(){
    	return this.attributes;
    }

    @Override
    public <T> T getAs(String name) {
        return (T) attributes.get(name);
    }

    public void addAttribute(String Key, Object value){
        attributes.put(Key,value);
    }

    /**
     * Parse the attributes from external String-representation to internal, attribute-specific data types
     * <p>
     * For the {@link ContractType} indicated in attribute "ContractType" the method goes through the list
     * of supported attributes and tries to parse these to their respective data type as indicated in the
     * ACTUS data dictionary ({@linktourl https://www.actusfrf.org/data-dictionary}).
     * <p>
     * For all attributes mandatory to a certain "ContractType" the method expects a not-{@code null} return value
     * of method {@code get} of the {@code Map<String,String>} method parameter. For non-mandatory attributes, a
     * {@code null} return value is allowed and treated as that the attribute is not specified. Some attributes may
     * be mandatory conditional to the value of other attributes. Be referred to the ACTUS data dictionary
     * for details.
     *
     * @param contractAttributes an external, raw (String) data representation of the set of attributes
     * @return an instance of ContractModel containing the attributes provided with the method argument
     * @throws AttributeConversionException if an attribute cannot be parsed to its data type
     */
    public static ContractModel parse(Map<String, Object> contractAttributes) {
    		
    		HashMap<String, Object> map = new HashMap<>();
            Map<String, Object> attributes = contractAttributes;
            
            // parse all attributes relevant to behavior risk factor models
            try {
            	map.put("contractID",attributes.get("contractID"));
            	map.put("contractType", attributes.get("contractType"));
            	map.put("initialExchangeDate", LocalDateTime.parse((String)attributes.get("initialExchangeDate")));
            	List<String> mdls = (List<String>)attributes.get("prepaymentModels");
            	System.out.println("**** fnp031: mdls = <" + mdls + ">");
            	map.put("prepaymentModels", mdls);
       
            } catch (Exception e) {
            throw new AttributeConversionException();
            }
       
           return new ContractModel(map);
}
    
}
