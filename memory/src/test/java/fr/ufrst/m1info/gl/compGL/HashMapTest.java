package fr.ufrst.m1info.gl.compGL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class HashMapTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HashMapTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HashMapTest.class );
    }


    public void testApp()
    {

        HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
        map.put(4,81);
        assertEquals(Optional.of(81),Optional.of(map.get(4)));
    }
}
