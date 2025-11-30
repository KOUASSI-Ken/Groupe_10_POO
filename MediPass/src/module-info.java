/**
 * 
 */
/**
 * 
 */
module MediPass {
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.annotation;
	opens medipass.models to com.fasterxml.jackson.databind;
	exports medipass;
}