package org.adaptiveplatform.codegenerator.acceptance {
	
  	import mx.collections.ArrayCollection;
  	import mx.collections.ArrayCollection;
	import org.adaptiveplatform.communication.ResultHandler;

	/**
	 * NOTE: This file is autogenerated and will be overwritten every time.
	 */
	public interface SampleDao {
	
		/**
		 * @returns int 
		 */
		function count(arrayCollection:ArrayCollection):ResultHandler;
		
		/**
		 * @returns SampleDto 
		 */
		function getUser(number:Number):ResultHandler;
		
		/**
		 * @returns ArrayCollection of SampleDto
		 */
		function query(string:String):ResultHandler;
		
	}
}