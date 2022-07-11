package openadmin.util.validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;





/**
 *  Class that contains methods for defining various number formats
 *	@version  1.0
 *  @author Alfred Oliver
*/
public class FormatNumero {

	public static Double format2Decimals(Double pNumero){
		
		String s = "#0.00";
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();
		dformater_rules.setDecimalSeparator ('.');
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        Double valor = Double.parseDouble(decimalFormat.format(pNumero));
        
        return valor;
	}
	
	public static Double format4Decimals(Double pNumero){
		
		String s = "#0.0000";
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();
		dformater_rules.setDecimalSeparator ('.');
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);
        decimalFormat.setMaximumFractionDigits(4);
        decimalFormat.setMinimumFractionDigits(0);
        Double valor = Double.parseDouble(decimalFormat.format(pNumero));
        
        return valor;
	}
	
	public static Double format6Decimals(Double pNumero){
		
		String s = "#0.000000";
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();
		dformater_rules.setDecimalSeparator ('.');
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);
        decimalFormat.setMaximumFractionDigits(6);
        decimalFormat.setMinimumFractionDigits(0);
        Double valor = Double.parseDouble(decimalFormat.format(pNumero));
        
        return valor;
	}
	
	public static String formatoEuro (Double pNumero){
		
		Locale defaultLocale = new Locale("es", "ES", "EURO");
    	NumberFormat nf = NumberFormat.getCurrencyInstance(defaultLocale);
    	String valor = nf.format(pNumero);
    	
    	return valor;
	}
	
	public static String formatNumeric(Integer pNumero){
		
		//String s = "#0.00";
		String s = "#,###";		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();
		dformater_rules.setMonetaryDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(pNumero);
        
        return valor;
	}
	
	public static String formatNumero2Digits(String pNumero){
		
		String s = "00";		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();		
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(Integer.parseInt(pNumero));
        
        return valor;
	}
	
	public static String formatNumero3Digits(String pNumero){
		
		String s = "000";		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();		
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(Integer.parseInt(pNumero));
        
        return valor;
	}
	
	public static String formatNumero4Digits(String pNumero){
		
		String s = "0000";		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();		
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(Integer.parseInt(pNumero));
        
        return valor;
	}
	
	public static String formatNumero5Digits(String pNumero){
		
		String s = "00000";		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();		
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(Integer.parseInt(pNumero));
        
        return valor;
	}
	
	public static String formatNumero(String pNumero, String pFormato){
		
		String s = pFormato;		
		DecimalFormatSymbols dformater_rules = new DecimalFormatSymbols ();		
        DecimalFormat decimalFormat = new DecimalFormat(s, dformater_rules);       
        String valor = decimalFormat.format(Integer.parseInt(pNumero));
        
        return valor;
	}
	
	public static Double formatoNumeroDouble(String valor)
	{

	    int lastPosComas = -1;
	    int lastPosPuntos = -1;

	    lastPosComas = valor.lastIndexOf(",");
	    lastPosPuntos = valor.lastIndexOf(".");

	    if (lastPosComas > lastPosPuntos)
	    {
	        valor = valor.replace(".", "");
	        valor = valor.replace(",", ".");

	    }
	    else
	    {
	        valor = valor.replace(",", "");
	    }


	    Double valorDouble = new Double(valor);

	    return valorDouble;
	}
}
