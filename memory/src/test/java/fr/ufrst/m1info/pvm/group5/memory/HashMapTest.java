package fr.ufrst.m1info.pvm.group5.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest
{
    java.util.HashMap<String, Integer> oldMapComplete;
    HashMap<String, Integer> mapComplete;
    @BeforeEach
    void setUp(){
        oldMapComplete=new java.util.HashMap<String, Integer>();
        mapComplete=new HashMap<String, Integer>();
        oldMapComplete.put("OP",81);
        oldMapComplete.put("LN",4);
        oldMapComplete.put("CL",16);
        oldMapComplete.put("LH",44);
        oldMapComplete.put("GR",63);
        oldMapComplete.put("AKA",12);
        oldMapComplete.put("MV",33);
        oldMapComplete.put("YT",22);
        oldMapComplete.put("AA",23);
        oldMapComplete.put("CS",55);
        oldMapComplete.put("PG",10);
        oldMapComplete.put("FC",43);
        oldMapComplete.put("EO",31);
        oldMapComplete.put("OB",87);
        oldMapComplete.put("FA",14);
        oldMapComplete.put("LS",18);
        oldMapComplete.put("NH",27);
        oldMapComplete.put("GB",5);
        oldMapComplete.put("IH",6);
        for (String key : oldMapComplete.keySet()){
            mapComplete.put(key,oldMapComplete.get(key));
        }
    }

    @Test
    void testSize()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    void testIsEmpty()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.isEmpty(),map.isEmpty());
    }

    @Test
    void testGet()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get("TH"),map.get("TH"));
    }

    @Test
    void testGetIncorrectType()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get(4),map.get(4));
    }

    @Test
    void testContainsKey()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.get("TH"),map.get("TH"));
    }

    @Test
    void testContainsKeyIncorrectType()
    {
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        assertEquals(oldMap.containsKey(4),map.containsKey(4));
    }

    @Test
    void testPutFirstElementInteger()
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
    void testPutFirstElementStringAndInteger()
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
    void testPutSecondElement()
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
    void testPutSecondElementAndReplaceFirstElement()
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
    void testPutManyElements()
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
    }

    @Test
    void testSizeManyElements(){
        assertEquals(oldMapComplete.size(),mapComplete.size());
    }

    @Test
    void testGetManyElements(){
        assertEquals(oldMapComplete.get("LN"),mapComplete.get("LN"));
        assertEquals(oldMapComplete.get("OP"),mapComplete.get("OP"));
        assertEquals(oldMapComplete.get("CL"),mapComplete.get("CL"));
        assertEquals(oldMapComplete.get("LH"),mapComplete.get("LH"));
        assertEquals(oldMapComplete.get("GR"),mapComplete.get("GR"));
        assertEquals(oldMapComplete.get("AKA"),mapComplete.get("AKA"));
        assertEquals(oldMapComplete.get("MV"),mapComplete.get("MV"));
        assertEquals(oldMapComplete.get("YT"),mapComplete.get("YT"));
        assertEquals(oldMapComplete.get("CS"),mapComplete.get("CS"));
        assertEquals(oldMapComplete.get("AA"),mapComplete.get("AA"));
        assertEquals(oldMapComplete.get("FA"),mapComplete.get("FA"));
        assertEquals(oldMapComplete.get("LS"),mapComplete.get("LS"));
        assertEquals(oldMapComplete.get("PG"),mapComplete.get("PG"));
        assertEquals(oldMapComplete.get("FC"),mapComplete.get("FC"));
        assertEquals(oldMapComplete.get("EO"),mapComplete.get("EO"));
        assertEquals(oldMapComplete.get("OB"),mapComplete.get("OB"));
        assertEquals(oldMapComplete.get("NH"),mapComplete.get("NH"));
        assertEquals(oldMapComplete.get("GB"),mapComplete.get("GB"));
        assertEquals(oldMapComplete.get("IH"),mapComplete.get("IH"));
        assertEquals(oldMapComplete.get("LL"),mapComplete.get("LL"));
        assertEquals(oldMapComplete.get("MS"),mapComplete.get("MS"));
        assertEquals(oldMapComplete.get("AP"),mapComplete.get("AP"));
    }

    @Test
    void testContainsKeyManyElements(){
        assertEquals(oldMapComplete.containsKey("LN"),mapComplete.containsKey("LN"));
        assertEquals(oldMapComplete.containsKey("OP"),mapComplete.containsKey("OP"));
        assertEquals(oldMapComplete.containsKey("CL"),mapComplete.containsKey("CL"));
        assertEquals(oldMapComplete.containsKey("LH"),mapComplete.containsKey("LH"));
        assertEquals(oldMapComplete.containsKey("GR"),mapComplete.containsKey("GR"));
        assertEquals(oldMapComplete.containsKey("AKA"),mapComplete.containsKey("AKA"));
        assertEquals(oldMapComplete.containsKey("MV"),mapComplete.containsKey("MV"));
        assertEquals(oldMapComplete.containsKey("YT"),mapComplete.containsKey("YT"));
        assertEquals(oldMapComplete.containsKey("CS"),mapComplete.containsKey("CS"));
        assertEquals(oldMapComplete.containsKey("AA"),mapComplete.containsKey("AA"));
        assertEquals(oldMapComplete.containsKey("FA"),mapComplete.containsKey("FA"));
        assertEquals(oldMapComplete.containsKey("LS"),mapComplete.containsKey("LS"));
        assertEquals(oldMapComplete.containsKey("PG"),mapComplete.containsKey("PG"));
        assertEquals(oldMapComplete.containsKey("FC"),mapComplete.containsKey("FC"));
        assertEquals(oldMapComplete.containsKey("EO"),mapComplete.containsKey("EO"));
        assertEquals(oldMapComplete.containsKey("OB"),mapComplete.containsKey("OB"));
        assertEquals(oldMapComplete.containsKey("NH"),mapComplete.containsKey("NH"));
        assertEquals(oldMapComplete.containsKey("GB"),mapComplete.containsKey("GB"));
        assertEquals(oldMapComplete.containsKey("IH"),mapComplete.containsKey("IH"));
        assertEquals(oldMapComplete.containsKey("LL"),mapComplete.containsKey("LL"));
        assertEquals(oldMapComplete.containsKey("MS"),mapComplete.containsKey("MS"));
        assertEquals(oldMapComplete.containsKey("AP"),mapComplete.containsKey("AP"));
    }

    @Test
    void testRemoveExistingElement()
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
    void testRemoveNotExistingElement()
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
    void testRemoveManyElements()
    {
        assertEquals(oldMapComplete.remove("OP"),mapComplete.remove("OP"));
        assertEquals(oldMapComplete.remove("AP"),mapComplete.remove("AP"));
    }

    @Test
    void testSizeManyElementsRemoved(){
        oldMapComplete.remove("OP");
        oldMapComplete.remove("LN");
        oldMapComplete.remove("MV");
        mapComplete.remove("OP");
        mapComplete.remove("LN");
        mapComplete.remove("MV");
        assertEquals(oldMapComplete.size(),mapComplete.size());
    }

    @Test
    void testGetManyElementsRemoved(){
        oldMapComplete.remove("OP");
        oldMapComplete.remove("LN");
        oldMapComplete.remove("MV");
        mapComplete.remove("OP");
        mapComplete.remove("LN");
        mapComplete.remove("MV");
        assertEquals(oldMapComplete.get("LN"),mapComplete.get("LN"));
        assertEquals(oldMapComplete.get("OP"),mapComplete.get("OP"));
        assertEquals(oldMapComplete.get("CL"),mapComplete.get("CL"));
        assertEquals(oldMapComplete.get("LH"),mapComplete.get("LH"));
        assertEquals(oldMapComplete.get("GR"),mapComplete.get("GR"));
        assertEquals(oldMapComplete.get("AKA"),mapComplete.get("AKA"));
        assertEquals(oldMapComplete.get("MV"),mapComplete.get("MV"));
        assertEquals(oldMapComplete.get("YT"),mapComplete.get("YT"));
        assertEquals(oldMapComplete.get("CS"),mapComplete.get("CS"));
        assertEquals(oldMapComplete.get("AA"),mapComplete.get("AA"));
        assertEquals(oldMapComplete.get("FA"),mapComplete.get("FA"));
        assertEquals(oldMapComplete.get("LS"),mapComplete.get("LS"));
        assertEquals(oldMapComplete.get("PG"),mapComplete.get("PG"));
        assertEquals(oldMapComplete.get("FC"),mapComplete.get("FC"));
        assertEquals(oldMapComplete.get("EO"),mapComplete.get("EO"));
        assertEquals(oldMapComplete.get("OB"),mapComplete.get("OB"));
        assertEquals(oldMapComplete.get("NH"),mapComplete.get("NH"));
        assertEquals(oldMapComplete.get("GB"),mapComplete.get("GB"));
        assertEquals(oldMapComplete.get("IH"),mapComplete.get("IH"));
        assertEquals(oldMapComplete.get("LL"),mapComplete.get("LL"));
        assertEquals(oldMapComplete.get("MS"),mapComplete.get("MS"));
        assertEquals(oldMapComplete.get("AP"),mapComplete.get("AP"));
    }

    @Test
    void testContainsKeyManyElementsRemoved(){
        oldMapComplete.remove("OP");
        oldMapComplete.remove("LN");
        oldMapComplete.remove("MV");
        mapComplete.remove("OP");
        mapComplete.remove("LN");
        mapComplete.remove("MV");
        assertEquals(oldMapComplete.containsKey("LN"),mapComplete.containsKey("LN"));
        assertEquals(oldMapComplete.containsKey("OP"),mapComplete.containsKey("OP"));
        assertEquals(oldMapComplete.containsKey("CL"),mapComplete.containsKey("CL"));
        assertEquals(oldMapComplete.containsKey("LH"),mapComplete.containsKey("LH"));
        assertEquals(oldMapComplete.containsKey("GR"),mapComplete.containsKey("GR"));
        assertEquals(oldMapComplete.containsKey("AKA"),mapComplete.containsKey("AKA"));
        assertEquals(oldMapComplete.containsKey("MV"),mapComplete.containsKey("MV"));
        assertEquals(oldMapComplete.containsKey("YT"),mapComplete.containsKey("YT"));
        assertEquals(oldMapComplete.containsKey("CS"),mapComplete.containsKey("CS"));
        assertEquals(oldMapComplete.containsKey("AA"),mapComplete.containsKey("AA"));
        assertEquals(oldMapComplete.containsKey("FA"),mapComplete.containsKey("FA"));
        assertEquals(oldMapComplete.containsKey("LS"),mapComplete.containsKey("LS"));
        assertEquals(oldMapComplete.containsKey("PG"),mapComplete.containsKey("PG"));
        assertEquals(oldMapComplete.containsKey("FC"),mapComplete.containsKey("FC"));
        assertEquals(oldMapComplete.containsKey("EO"),mapComplete.containsKey("EO"));
        assertEquals(oldMapComplete.containsKey("OB"),mapComplete.containsKey("OB"));
        assertEquals(oldMapComplete.containsKey("NH"),mapComplete.containsKey("NH"));
        assertEquals(oldMapComplete.containsKey("GB"),mapComplete.containsKey("GB"));
        assertEquals(oldMapComplete.containsKey("IH"),mapComplete.containsKey("IH"));
        assertEquals(oldMapComplete.containsKey("LL"),mapComplete.containsKey("LL"));
        assertEquals(oldMapComplete.containsKey("MS"),mapComplete.containsKey("MS"));
        assertEquals(oldMapComplete.containsKey("AP"),mapComplete.containsKey("AP"));
    }

    @Test
    void testRemoveSameHashCode(){
        HashMap<NegativeHashCode,Integer> map=new HashMap<>(8);
        java.util.HashMap<NegativeHashCode, Integer> oldMap=new java.util.HashMap<>();
        assertEquals(oldMap.put(new NegativeHashCode(81),81),map.put(new NegativeHashCode(81),81));
        assertEquals(oldMap.put(new NegativeHashCode(-81),-81),map.put(new NegativeHashCode(-81),-81));
        assertEquals(oldMap.remove(new NegativeHashCode(-81)),map.remove(new NegativeHashCode(-81)));
        assertEquals(oldMap.get(new NegativeHashCode(81)),map.get(new NegativeHashCode(81)));
        assertEquals(oldMap.get(new NegativeHashCode(-81)),map.get(new NegativeHashCode(-81)));

    }

    @Test
    void testSizeManyElementsCleared(){
        oldMapComplete.clear();
        mapComplete.clear();
        assertEquals(oldMapComplete.size(),mapComplete.size());
    }

    @Test
    void testGetManyElementsCleared(){
        oldMapComplete.clear();
        mapComplete.clear();
        assertEquals(oldMapComplete.get("LN"),mapComplete.get("LN"));
        assertEquals(oldMapComplete.get("OP"),mapComplete.get("OP"));
        assertEquals(oldMapComplete.get("CL"),mapComplete.get("CL"));
        assertEquals(oldMapComplete.get("LH"),mapComplete.get("LH"));
        assertEquals(oldMapComplete.get("GR"),mapComplete.get("GR"));
        assertEquals(oldMapComplete.get("AKA"),mapComplete.get("AKA"));
        assertEquals(oldMapComplete.get("MV"),mapComplete.get("MV"));
        assertEquals(oldMapComplete.get("YT"),mapComplete.get("YT"));
        assertEquals(oldMapComplete.get("CS"),mapComplete.get("CS"));
        assertEquals(oldMapComplete.get("AA"),mapComplete.get("AA"));
        assertEquals(oldMapComplete.get("FA"),mapComplete.get("FA"));
        assertEquals(oldMapComplete.get("LS"),mapComplete.get("LS"));
        assertEquals(oldMapComplete.get("PG"),mapComplete.get("PG"));
        assertEquals(oldMapComplete.get("FC"),mapComplete.get("FC"));
        assertEquals(oldMapComplete.get("EO"),mapComplete.get("EO"));
        assertEquals(oldMapComplete.get("OB"),mapComplete.get("OB"));
        assertEquals(oldMapComplete.get("NH"),mapComplete.get("NH"));
        assertEquals(oldMapComplete.get("GB"),mapComplete.get("GB"));
        assertEquals(oldMapComplete.get("IH"),mapComplete.get("IH"));
        assertEquals(oldMapComplete.get("LL"),mapComplete.get("LL"));
        assertEquals(oldMapComplete.get("MS"),mapComplete.get("MS"));
        assertEquals(oldMapComplete.get("AP"),mapComplete.get("AP"));
    }

    @Test
    void testContainsKeyManyElementsCleared(){
        oldMapComplete.clear();
        mapComplete.clear();
        assertEquals(oldMapComplete.containsKey("LN"),mapComplete.containsKey("LN"));
        assertEquals(oldMapComplete.containsKey("OP"),mapComplete.containsKey("OP"));
        assertEquals(oldMapComplete.containsKey("CL"),mapComplete.containsKey("CL"));
        assertEquals(oldMapComplete.containsKey("LH"),mapComplete.containsKey("LH"));
        assertEquals(oldMapComplete.containsKey("GR"),mapComplete.containsKey("GR"));
        assertEquals(oldMapComplete.containsKey("AKA"),mapComplete.containsKey("AKA"));
        assertEquals(oldMapComplete.containsKey("MV"),mapComplete.containsKey("MV"));
        assertEquals(oldMapComplete.containsKey("YT"),mapComplete.containsKey("YT"));
        assertEquals(oldMapComplete.containsKey("CS"),mapComplete.containsKey("CS"));
        assertEquals(oldMapComplete.containsKey("AA"),mapComplete.containsKey("AA"));
        assertEquals(oldMapComplete.containsKey("FA"),mapComplete.containsKey("FA"));
        assertEquals(oldMapComplete.containsKey("LS"),mapComplete.containsKey("LS"));
        assertEquals(oldMapComplete.containsKey("PG"),mapComplete.containsKey("PG"));
        assertEquals(oldMapComplete.containsKey("FC"),mapComplete.containsKey("FC"));
        assertEquals(oldMapComplete.containsKey("EO"),mapComplete.containsKey("EO"));
        assertEquals(oldMapComplete.containsKey("OB"),mapComplete.containsKey("OB"));
        assertEquals(oldMapComplete.containsKey("NH"),mapComplete.containsKey("NH"));
        assertEquals(oldMapComplete.containsKey("GB"),mapComplete.containsKey("GB"));
        assertEquals(oldMapComplete.containsKey("IH"),mapComplete.containsKey("IH"));
        assertEquals(oldMapComplete.containsKey("LL"),mapComplete.containsKey("LL"));
        assertEquals(oldMapComplete.containsKey("MS"),mapComplete.containsKey("MS"));
        assertEquals(oldMapComplete.containsKey("AP"),mapComplete.containsKey("AP"));
    }

    @Test
    void testValues()
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
    void testNegativeHashCode(){
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
    void testKeyNull(){
        HashMap<NegativeHashCode,Integer> map=new HashMap<>(8);
        java.util.HashMap<NegativeHashCode, Integer> oldMap=new java.util.HashMap<>();
        assertEquals(oldMap.put(null,0),map.put(null,0));
        assertEquals(oldMap.get(null),map.get(null));
        assertEquals(oldMap.containsKey(null),map.containsKey(null));
        assertEquals(oldMap.remove(null),map.remove(null));
        assertEquals(oldMap.get(null),map.get(null));
    }

    @Test
    void testValuesEmpty()
    {
        HashMap<String,Integer> map=new HashMap<>();
        Collection<Integer> colMap=map.values();
        assertTrue(colMap.isEmpty());

    }

    @Test
    void testConstructorExceptionCapacity()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(-5,0.75F));
    }

    @Test
    void testConstructorExceptionLoadFactor()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(3,-1.0F));
    }



}
