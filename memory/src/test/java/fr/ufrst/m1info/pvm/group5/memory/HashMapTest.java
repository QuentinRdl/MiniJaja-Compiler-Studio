package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.Test;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class HashMapTest
{

    @Test
    public void testSize()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testIsEmpty()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.isEmpty(),map.isEmpty());
    }

    @Test
    public void testGet()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get("TH"),map.get("TH"));
    }

    @Test
    public void testGetIncorrectType()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get(4),map.get(4));
    }

    @Test
    public void testContainsKey()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get("TH"),map.get("TH"));
    }

    @Test
    public void testContainsKeyIncorrectType()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.containsKey(4),map.containsKey(4));
    }

    @Test
    public void testPutFirstElementInteger()
    {
        HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
        java.util.HashMap<Integer, Integer> oldMap=new java.util.HashMap<Integer, Integer>();
        assertEquals(oldMap.put(4,81),map.put(4,81));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get(4),map.get(4));
        assertEquals(oldMap.get(81),map.get(81));
        assertEquals(oldMap.containsKey(4),map.containsKey(4));
        assertEquals(oldMap.containsKey(81),map.containsKey(81));
    }

    @Test
    public void testPutFirstElementStringAndInteger()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
    }

    @Test
    public void testPutSecondElement()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
    }

    @Test
    public void testPutSecondElementAndReplaceFirstElement()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.put("OP",1),map.put("OP",1));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
    }

    @Test
    public void testPutManyElements()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.put("CL",15),map.put("CL",15));
        assertEquals(oldMap.put("CL",16),map.put("CL",16));
        assertEquals(oldMap.put("LH",44),map.put("LH",44));
        assertEquals(oldMap.put("GR",53),map.put("GR",53));
        assertEquals(oldMap.put("GR",63),map.put("GR",63));
        assertEquals(oldMap.put("AKA",12),map.put("AKA",12));
        assertEquals(oldMap.put("MV",1),map.put("MV",1));
        assertEquals(oldMap.put("YT",22),map.put("YT",22));
        assertEquals(oldMap.put("AA",23),map.put("AA",23));
        assertEquals(oldMap.put("CS",55),map.put("CS",55));
        assertEquals(oldMap.put("PG",10),map.put("PG",10));
        assertEquals(oldMap.put("FC",43),map.put("FC",43));
        assertEquals(oldMap.put("EO",31),map.put("EO",31));
        assertEquals(oldMap.put("OB",87),map.put("OB",87));
        assertEquals(oldMap.put("FA",14),map.put("FA",14));
        assertEquals(oldMap.put("LS",18),map.put("LS",18));
        assertEquals(oldMap.put("NH",27),map.put("NH",27));
        assertEquals(oldMap.put("GB",5),map.put("GB",5));
        assertEquals(oldMap.put("IH",5),map.put("IH",5));
        assertEquals(oldMap.put("IH",6),map.put("IH",6));
        assertEquals(oldMap.put("LL",30),map.put("LL",30));
        assertEquals(oldMap.put("OP",1),map.put("OP",1));
        assertEquals(oldMap.put("MV",33),map.put("MV",33));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.get("CL"),map.get("CL"));
        assertEquals(oldMap.get("LH"),map.get("LH"));
        assertEquals(oldMap.get("GR"),map.get("GR"));
        assertEquals(oldMap.get("AKA"),map.get("AKA"));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.get("YT"),map.get("YT"));
        assertEquals(oldMap.get("CS"),map.get("CS"));
        assertEquals(oldMap.get("AA"),map.get("AA"));
        assertEquals(oldMap.get("FA"),map.get("FA"));
        assertEquals(oldMap.get("LS"),map.get("LS"));
        assertEquals(oldMap.get("PG"),map.get("PG"));
        assertEquals(oldMap.get("FC"),map.get("FC"));
        assertEquals(oldMap.get("EO"),map.get("EO"));
        assertEquals(oldMap.get("OB"),map.get("OB"));
        assertEquals(oldMap.get("NH"),map.get("NH"));
        assertEquals(oldMap.get("GB"),map.get("GB"));
        assertEquals(oldMap.get("IH"),map.get("IH"));
        assertEquals(oldMap.get("LL"),map.get("LL"));
        assertEquals(oldMap.get("MS"),map.get("MS"));
        assertEquals(oldMap.get("AP"),map.get("AP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
        assertEquals(oldMap.containsKey("CL"),map.containsKey("CL"));
        assertEquals(oldMap.containsKey("LH"),map.containsKey("LH"));
        assertEquals(oldMap.containsKey("GR"),map.containsKey("GR"));
        assertEquals(oldMap.containsKey("AKA"),map.containsKey("AKA"));
        assertEquals(oldMap.containsKey("MV"),map.containsKey("MV"));
        assertEquals(oldMap.containsKey("YT"),map.containsKey("YT"));
        assertEquals(oldMap.containsKey("CS"),map.containsKey("CS"));
        assertEquals(oldMap.containsKey("AA"),map.containsKey("AA"));
        assertEquals(oldMap.containsKey("FA"),map.containsKey("FA"));
        assertEquals(oldMap.containsKey("LS"),map.containsKey("LS"));
        assertEquals(oldMap.containsKey("PG"),map.containsKey("PG"));
        assertEquals(oldMap.containsKey("FC"),map.containsKey("FC"));
        assertEquals(oldMap.containsKey("EO"),map.containsKey("EO"));
        assertEquals(oldMap.containsKey("OB"),map.containsKey("OB"));
        assertEquals(oldMap.containsKey("NH"),map.containsKey("NH"));
        assertEquals(oldMap.containsKey("GB"),map.containsKey("GB"));
        assertEquals(oldMap.containsKey("IH"),map.containsKey("IH"));
        assertEquals(oldMap.containsKey("LL"),map.containsKey("LL"));
        assertEquals(oldMap.containsKey("MS"),map.containsKey("MS"));
        assertEquals(oldMap.containsKey("AP"),map.containsKey("AP"));
    }



    @Test
    public void testRemoveExistingElement()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.remove("OP"),map.remove("OP"));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
    }

    @Test
    public void testRemoveNotExistingElement()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.remove("AP"),map.remove("AP"));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
    }

    @Test
    public void testRemoveManyElements()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.put("OP",81),map.put("OP",81));
        assertEquals(oldMap.put("LN",4),map.put("LN",4));
        assertEquals(oldMap.put("CL",15),map.put("CL",15));
        assertEquals(oldMap.put("CL",16),map.put("CL",16));
        assertEquals(oldMap.put("LH",44),map.put("LH",44));
        assertEquals(oldMap.put("GR",53),map.put("GR",53));
        assertEquals(oldMap.put("GR",63),map.put("GR",63));
        assertEquals(oldMap.put("AKA",12),map.put("AKA",12));
        assertEquals(oldMap.put("MV",1),map.put("MV",1));
        assertEquals(oldMap.put("YT",22),map.put("YT",22));
        assertEquals(oldMap.put("AA",23),map.put("AA",23));
        assertEquals(oldMap.put("CS",55),map.put("CS",55));
        assertEquals(oldMap.put("PG",10),map.put("PG",10));
        assertEquals(oldMap.put("FC",43),map.put("FC",43));
        assertEquals(oldMap.put("EO",31),map.put("EO",31));
        assertEquals(oldMap.put("OB",87),map.put("OB",87));
        assertEquals(oldMap.put("FA",14),map.put("FA",14));
        assertEquals(oldMap.put("LS",18),map.put("LS",18));
        assertEquals(oldMap.put("NH",27),map.put("NH",27));
        assertEquals(oldMap.put("GB",5),map.put("GB",5));
        assertEquals(oldMap.put("IH",5),map.put("IH",5));
        assertEquals(oldMap.put("IH",6),map.put("IH",6));
        assertEquals(oldMap.put("LL",30),map.put("LL",30));
        assertEquals(oldMap.put("OP",1),map.put("OP",1));
        assertEquals(oldMap.put("MV",33),map.put("MV",33));
        assertEquals(oldMap.remove("OP"),map.remove("OP"));
        assertEquals(oldMap.remove("AP"),map.remove("AP"));
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.get("CL"),map.get("CL"));
        assertEquals(oldMap.get("LH"),map.get("LH"));
        assertEquals(oldMap.get("GR"),map.get("GR"));
        assertEquals(oldMap.get("AKA"),map.get("AKA"));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.get("YT"),map.get("YT"));
        assertEquals(oldMap.get("CS"),map.get("CS"));
        assertEquals(oldMap.get("AA"),map.get("AA"));
        assertEquals(oldMap.get("FA"),map.get("FA"));
        assertEquals(oldMap.get("LS"),map.get("LS"));
        assertEquals(oldMap.get("PG"),map.get("PG"));
        assertEquals(oldMap.get("FC"),map.get("FC"));
        assertEquals(oldMap.get("EO"),map.get("EO"));
        assertEquals(oldMap.get("OB"),map.get("OB"));
        assertEquals(oldMap.get("NH"),map.get("NH"));
        assertEquals(oldMap.get("GB"),map.get("GB"));
        assertEquals(oldMap.get("IH"),map.get("IH"));
        assertEquals(oldMap.get("LL"),map.get("LL"));
        assertEquals(oldMap.get("MS"),map.get("MS"));
        assertEquals(oldMap.get("AP"),map.get("AP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
        assertEquals(oldMap.containsKey("CL"),map.containsKey("CL"));
        assertEquals(oldMap.containsKey("LH"),map.containsKey("LH"));
        assertEquals(oldMap.containsKey("GR"),map.containsKey("GR"));
        assertEquals(oldMap.containsKey("AKA"),map.containsKey("AKA"));
        assertEquals(oldMap.containsKey("MV"),map.containsKey("MV"));
        assertEquals(oldMap.containsKey("YT"),map.containsKey("YT"));
        assertEquals(oldMap.containsKey("CS"),map.containsKey("CS"));
        assertEquals(oldMap.containsKey("AA"),map.containsKey("AA"));
        assertEquals(oldMap.containsKey("FA"),map.containsKey("FA"));
        assertEquals(oldMap.containsKey("LS"),map.containsKey("LS"));
        assertEquals(oldMap.containsKey("PG"),map.containsKey("PG"));
        assertEquals(oldMap.containsKey("FC"),map.containsKey("FC"));
        assertEquals(oldMap.containsKey("EO"),map.containsKey("EO"));
        assertEquals(oldMap.containsKey("OB"),map.containsKey("OB"));
        assertEquals(oldMap.containsKey("NH"),map.containsKey("NH"));
        assertEquals(oldMap.containsKey("GB"),map.containsKey("GB"));
        assertEquals(oldMap.containsKey("IH"),map.containsKey("IH"));
        assertEquals(oldMap.containsKey("LL"),map.containsKey("LL"));
        assertEquals(oldMap.containsKey("MS"),map.containsKey("MS"));
        assertEquals(oldMap.containsKey("AP"),map.containsKey("AP"));
    }

    @Test
    public void testRemoveSameHashCode(){
        HashMap<NegativeHashCode,Integer> map=new HashMap<>(8);
        java.util.HashMap<NegativeHashCode, Integer> oldMap=new java.util.HashMap<>();
        assertEquals(oldMap.put(new NegativeHashCode(81),81),map.put(new NegativeHashCode(81),81));
        assertEquals(oldMap.put(new NegativeHashCode(-81),-81),map.put(new NegativeHashCode(-81),-81));
        assertEquals(oldMap.remove(new NegativeHashCode(-81)),map.remove(new NegativeHashCode(-81)));
        assertEquals(oldMap.get(new NegativeHashCode(81)),map.get(new NegativeHashCode(81)));
        assertEquals(oldMap.get(new NegativeHashCode(-81)),map.get(new NegativeHashCode(-81)));

    }

    @Test
    public void testClear()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        oldMap.put("CL",16);
        oldMap.put("LH",44);
        oldMap.put("GR",63);
        oldMap.put("AKA",12);
        oldMap.put("MV",33);
        oldMap.put("YT",22);
        oldMap.put("AA",23);
        oldMap.put("CS",55);
        oldMap.put("PG",10);
        oldMap.put("FC",43);
        oldMap.put("EO",31);
        oldMap.put("OB",87);
        oldMap.put("FA",14);
        oldMap.put("LS",18);
        oldMap.put("NH",27);
        oldMap.put("GB",5);
        oldMap.put("IH",6);
        HashMap<String,Integer> map=new HashMap<>();
        for (String key : oldMap.keySet()){
            map.put(key,oldMap.get(key));
        }
        map.clear();
        oldMap.clear();
        assertEquals(oldMap.size(),map.size());
        assertEquals(oldMap.get("LN"),map.get("LN"));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.get("CL"),map.get("CL"));
        assertEquals(oldMap.get("LH"),map.get("LH"));
        assertEquals(oldMap.get("GR"),map.get("GR"));
        assertEquals(oldMap.get("AKA"),map.get("AKA"));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.get("YT"),map.get("YT"));
        assertEquals(oldMap.get("CS"),map.get("CS"));
        assertEquals(oldMap.get("AA"),map.get("AA"));
        assertEquals(oldMap.get("FA"),map.get("FA"));
        assertEquals(oldMap.get("LS"),map.get("LS"));
        assertEquals(oldMap.get("PG"),map.get("PG"));
        assertEquals(oldMap.get("FC"),map.get("FC"));
        assertEquals(oldMap.get("EO"),map.get("EO"));
        assertEquals(oldMap.get("OB"),map.get("OB"));
        assertEquals(oldMap.get("NH"),map.get("NH"));
        assertEquals(oldMap.get("GB"),map.get("GB"));
        assertEquals(oldMap.get("IH"),map.get("IH"));
        assertEquals(oldMap.get("LL"),map.get("LL"));
        assertEquals(oldMap.get("MS"),map.get("MS"));
        assertEquals(oldMap.get("AP"),map.get("AP"));
        assertEquals(oldMap.containsKey("LN"),map.containsKey("LN"));
        assertEquals(oldMap.containsKey("OP"),map.containsKey("OP"));
        assertEquals(oldMap.containsKey("CL"),map.containsKey("CL"));
        assertEquals(oldMap.containsKey("LH"),map.containsKey("LH"));
        assertEquals(oldMap.containsKey("GR"),map.containsKey("GR"));
        assertEquals(oldMap.containsKey("AKA"),map.containsKey("AKA"));
        assertEquals(oldMap.containsKey("MV"),map.containsKey("MV"));
        assertEquals(oldMap.containsKey("YT"),map.containsKey("YT"));
        assertEquals(oldMap.containsKey("CS"),map.containsKey("CS"));
        assertEquals(oldMap.containsKey("AA"),map.containsKey("AA"));
        assertEquals(oldMap.containsKey("FA"),map.containsKey("FA"));
        assertEquals(oldMap.containsKey("LS"),map.containsKey("LS"));
        assertEquals(oldMap.containsKey("PG"),map.containsKey("PG"));
        assertEquals(oldMap.containsKey("FC"),map.containsKey("FC"));
        assertEquals(oldMap.containsKey("EO"),map.containsKey("EO"));
        assertEquals(oldMap.containsKey("OB"),map.containsKey("OB"));
        assertEquals(oldMap.containsKey("NH"),map.containsKey("NH"));
        assertEquals(oldMap.containsKey("GB"),map.containsKey("GB"));
        assertEquals(oldMap.containsKey("IH"),map.containsKey("IH"));
        assertEquals(oldMap.containsKey("LL"),map.containsKey("LL"));
        assertEquals(oldMap.containsKey("MS"),map.containsKey("MS"));
        assertEquals(oldMap.containsKey("AP"),map.containsKey("AP"));
    }

    @Test
    public void testValues()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        oldMap.put("CL",16);
        oldMap.put("LH",44);
        oldMap.put("GR",63);
        oldMap.put("AKA",12);
        oldMap.put("MV",33);
        oldMap.put("YT",22);
        oldMap.put("AA",23);
        oldMap.put("CS",55);
        oldMap.put("PG",10);
        oldMap.put("FC",43);
        oldMap.put("EO",31);
        oldMap.put("OB",87);
        oldMap.put("FA",14);
        oldMap.put("LS",18);
        oldMap.put("NH",27);
        oldMap.put("GB",5);
        oldMap.put("IH",6);
        oldMap.put("LL",6);
        HashMap<String,Integer> map=new HashMap<>();
        for (String key : oldMap.keySet()){
            map.put(key,oldMap.get(key));
        }
        Collection<Integer> colOldMap=oldMap.values();
        Collection<Integer> colMap=map.values();
        assertEquals(colOldMap.size(),colMap.size());
        for (Integer value : colOldMap){
            assertTrue(colMap.contains(value));
        }

    }

    @Test
    public void testNegativeHashCode(){
        HashMap<NegativeHashCode,Integer> map=new HashMap<>(8);
        java.util.HashMap<NegativeHashCode, Integer> oldMap=new java.util.HashMap<>();
        assertEquals(oldMap.put(new NegativeHashCode(81),81),map.put(new NegativeHashCode(81),81));
        assertEquals(oldMap.put(new NegativeHashCode(8),8),map.put(new NegativeHashCode(8),8));
        assertEquals(oldMap.put(new NegativeHashCode(429),429),map.put(new NegativeHashCode(429),429));
        assertEquals(oldMap.put(new NegativeHashCode(-81),-81),map.put(new NegativeHashCode(-81),-81));
        assertEquals(oldMap.put(new NegativeHashCode(24),24),map.put(new NegativeHashCode(24),24));
        assertEquals(oldMap.get(new NegativeHashCode(81)),map.get(new NegativeHashCode(81)));
        assertEquals(oldMap.get(new NegativeHashCode(8)),map.get(new NegativeHashCode(8)));
        assertEquals(oldMap.get(new NegativeHashCode(429)),map.get(new NegativeHashCode(429)));
        assertEquals(oldMap.get(new NegativeHashCode(-81)),map.get(new NegativeHashCode(-81)));
        assertEquals(oldMap.get(new NegativeHashCode(24)),map.get(new NegativeHashCode(24)));
    }

    @Test
    public void testKeyNull(){
        HashMap<NegativeHashCode,Integer> map=new HashMap<>(8);
        java.util.HashMap<NegativeHashCode, Integer> oldMap=new java.util.HashMap<>();
        assertEquals(oldMap.put(null,0),map.put(null,0));
        assertEquals(oldMap.get(null),map.get(null));
        assertEquals(oldMap.containsKey(null),map.containsKey(null));
        assertEquals(oldMap.remove(null),map.remove(null));
        assertEquals(oldMap.get(null),map.get(null));
    }

    @Test
    public void testValuesEmpty()
    {
        HashMap<String,Integer> map=new HashMap<>();
        Collection<Integer> colMap=map.values();
        assertTrue(colMap.isEmpty());

    }

    @Test
    public void testConstructorExceptionCapacity()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(-5,0.75F));
    }

    @Test
    public void testConstructorExceptionLoadFactor()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(3,-1.0F));
    }



}
