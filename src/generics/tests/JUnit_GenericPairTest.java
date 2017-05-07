package generics.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import generics.templates.GenericPair;

/**
 * A test for a 2-tuple (pair) of <em> things </em><br><br>
 * For teaching  advanced OO concepts in Java - genericity<br><br>
 * 
 * Uses <b> JUnit</b>  to test template  class {@link GenericPair}
 *
 * @version 1
 * @author J Paul Gibson
 */
public class JUnit_GenericPairTest {

	/**
	 * A pair of integers
	 */
	GenericPair <Integer> poi;
	
	/**
	 * A copy of the pair of Integers {@code poi}
	 */
	GenericPair <Integer> poi_copy;
	
	/**
	 * A pair of characters
	 */
	GenericPair <Character> poc;
	
	/**
	 * A copy of the pair of Characters {@code poc}
	 */
	GenericPair <Character> poc_copy;
	
	/**
	 * A pair of a pair of Integers
	 */
	GenericPair <GenericPair <Integer>> popoi;
	
	/**
	 * A pair of a pair of Characters
	 */
	GenericPair <GenericPair <Character>> popoc;

	/**
	 * A generic pair of pairs
	 */
	GenericPair <GenericPair <?>> pop; 
	
	/**
	 * Initialise the test variables
	 * <ul> 
	 * <li> {@code poi} and {@code poi_copy} as pair of Integers (0,0) </li>
	 * <li> {@code poc} and {@code poc_copy} as a pair of Characters ('a', 'b') </li>
	 * <li> {@code popoi} as (('1','2') , ('3','4') </li>
	 * <li> {@code popoc} as (('a','b') , ('c','d')) </li>
	 * <li> {@code pop} as (('1','2') , ('c','d')) </li>
	 * </ul>
	 */
	@Before
	public void setUp()  {
		
		poi = new GenericPair <Integer>(0,0);
		poc = new GenericPair <Character>('a', 'b');
		poi_copy = new GenericPair <Integer> (poi);
		poc_copy = new GenericPair <Character> (poc);
		
		popoi = new GenericPair <GenericPair <Integer>>( new GenericPair <Integer>(1,2), new GenericPair <Integer>(3,4));
		popoc = new GenericPair <GenericPair <Character>>( new GenericPair <Character>('a', 'b'), new GenericPair <Character>('c', 'd'));
		pop = new GenericPair <GenericPair <?>>( new GenericPair <Integer>(1,2), new GenericPair <Character>('c', 'd'));
	}
	
	/**
	 * sets all test variables to null
	 */
	@After
	public void tearDown() {
		 poi = null;
		 poc = null;
		 poi_copy = null;
		 poc_copy = null;
		 popoi = null;
		 popoc = null;
		 pop = null;
	}
	
	/**
	 * Tests method {@link GenericPair#toString()}
	 */
	@Test
	public void testToString() {
		
		Assert.assertEquals(poi.toString(), "(0, 0)");
		Assert.assertEquals(poc.toString(), "(a, b)");
		Assert.assertEquals(popoi.toString(), "((1, 2), (3, 4))");
		Assert.assertEquals(popoc.toString(), "((a, b), (c, d))");
		Assert.assertEquals(pop.toString(), "((1, 2), (c, d))");
	}
	
	/**
	 * Tests method {@link GenericPair#swap(GenericPair)}
	 */
	@Test
	public void testSwap_static() {
		
		GenericPair.swap(poi); 
		GenericPair.swap(poc);
		GenericPair.swap(popoi); 
		GenericPair.swap(popoc);
		GenericPair.swap(pop); 
				
		Assert.assertEquals(poi.toString(), "(0, 0)");
		Assert.assertEquals(poc.toString(), "(b, a)");
		Assert.assertEquals(popoi.toString(), "((3, 4), (1, 2))");
	    Assert.assertEquals(popoc.toString(), "((c, d), (a, b))");
	    Assert.assertEquals(pop.toString(), "((c, d), (1, 2))");
	}
	
	/**
	 * Tests method {@link GenericPair#swap()} 
	 */
	@Test
	public void testSwap() {
		
		poi.swap(); 
		poc.swap();
		popoi.swap();
		popoc.swap();
		pop.swap();
		
		Assert.assertEquals(poi.toString(), "(0, 0)");
		Assert.assertEquals(poc.toString(), "(b, a)");
		Assert.assertEquals(popoi.toString(), "((3, 4), (1, 2))");
	    Assert.assertEquals(popoc.toString(), "((c, d), (a, b))");
	    Assert.assertEquals(pop.toString(), "((c, d), (1, 2))");
	}
	
	/**
	 * Tests method {@link GenericPair#GenericPair(GenericPair)} 
	 */
	@Test
	public void testCopyConstructor() {
		
		Assert.assertEquals(poi_copy.toString(), poi.toString());
		Assert.assertEquals(poc_copy.toString(), poc.toString());
	}
	
	/**
	 * Tests method {@link GenericPair#equals} 
	 */
	@Test
	public void testEquals() {
		
		
		Assert.assertTrue(poi.equals(poi));
		Assert.assertEquals(pop, pop);
		Assert.assertEquals(poc, poc);
		Assert.assertEquals(popoi, popoi);
		Assert.assertEquals(popoc, popoc);
		
		Assert.assertFalse(poi.equals(poc));
		Assert.assertEquals(poi, poi_copy);
		Assert.assertEquals(poc, poc_copy);
		poc_copy.swap();
		Assert.assertFalse(poc.equals(poc_copy));
		
		
		class Base {
			 
			  String name;
			  
			  Base (String name){this.name = name;}
				
			  public String toString(){ return name+ ":  Base Class";}
			  
				public boolean equals( Object thing){
					
					if (thing ==null) return false;
					if ( this == thing) return true;
					if (! (thing instanceof Base)) return false;
					
					Base that = (Base) thing;
					return ( this.name.equals(that.name) );
				}
			 }
		
		
		 class Derived extends Base {
				
			 Derived (String name){super(name);}
			 
			  public String toString(){ return super.toString()+" - Derived Class";}
			 }
		
		 
		 Base b1;
			Base b2;
			Base b3;
			Derived d1;
			Derived d2;
			Derived d3;
			
			b1 = new Base("one");
			b2 = new Base("one");
			b3 = new Base("two");
			d1 = new Derived("one");
			d2 = new Derived("one");
			d3 = new Derived("two");
			
		    GenericPair <Base> pob1 = new GenericPair <Base> (b1, b2);
		    GenericPair <Base> pob2 = new GenericPair <Base> (b2, b1);
		    GenericPair <Base> pob3 = new GenericPair <Base> (b1, b3);
		    
		    GenericPair <Derived> pod1 = new GenericPair <Derived> (d1, d2);
		    GenericPair <Derived> pod2 = new GenericPair <Derived> (d2, d1);
		    GenericPair <Derived> pod3 = new GenericPair <Derived> (d1, d3);
		    
		    Assert.assertEquals(pob1, pob2);
		    Assert.assertTrue(!pob1.equals(pob3));
		    Assert.assertEquals(pob3, pob3);
		    Assert.assertEquals(pod1, pod2);
		    Assert.assertTrue(!pob1.equals(pod3));
		    Assert.assertEquals(pod3, pod3);
		    Assert.assertEquals(pod1,pob1);
		    Assert.assertEquals(pod3,pob3);
		    
	}
	
}
