package fr.ufrst.m1info.gl.compGL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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
    public void testMapConstructor()
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
        HashMap<String,Integer> map=new HashMap<>(oldMap);
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
    public void testPutAll()
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
        map.putAll(oldMap);
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
        map.putAll(oldMap);
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
    public void testContainsValue()
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
        map.putAll(oldMap);
        for (int i=0;i<100;i++){
            assertEquals(oldMap.containsValue(i),map.containsValue(i));
        }

    }

    @Test
    public void testKeySet()
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
        map.putAll(oldMap);
        Set<String> setOldMap=oldMap.keySet();
        Set<String> setMap=map.keySet();
        assertEquals(setOldMap.size(),setMap.size());
        for (String key : setOldMap){
            assertTrue(setMap.contains(key));
        }

    }

    @Test
    public void testKeySetEmpty()
    {
        HashMap<String,Integer> map=new HashMap<>();
        Set<String> setMap=map.keySet();
        assertTrue(setMap.isEmpty());

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
        map.putAll(oldMap);
        Collection<Integer> colOldMap=oldMap.values();
        Collection<Integer> colMap=map.values();
        assertEquals(colOldMap.size(),colMap.size());
        for (Integer value : colOldMap){
            assertTrue(colMap.contains(value));
        }

    }

    @Test
    public void testValuesEmpty()
    {
        HashMap<String,Integer> map=new HashMap<>();
        Collection<Integer> colMap=map.values();
        assertTrue(colMap.isEmpty());

    }



    @Test
    public void testGetOrDefaultExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.getOrDefault("OP",1),map.getOrDefault("OP",1));
    }

    @Test
    public void testGetOrDefaultNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.getOrDefault("MV",1),map.getOrDefault("MV",1));
    }

    @Test
    public void testPutIfAbsentNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.putIfAbsent("MV",1),map.putIfAbsent("MV",1));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testPutIfAbsentAlreadyExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.putIfAbsent("OP",1),map.putIfAbsent("OP",1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testPutIfAbsentNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",null);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.putIfAbsent("OP",1),map.putIfAbsent("OP",1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testRemoveNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.remove("MV",1),map.remove("MV",1));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testRemoveIncorrectValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.remove("OP",1),map.remove("OP",1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testRemoveCorrectValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.remove("OP",81),map.remove("OP",81));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.replace("MV",33,1),map.replace("MV",33,1));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceNotExistingKey2()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.replace("MV",1),map.replace("MV",1));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.replace("OP",1),map.replace("OP",1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceIncorrectValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.replace("OP",4,1),map.replace("OP",4,1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceCorrectValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.replace("OP",81,1),map.replace("OP",81,1));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfAbsentExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfAbsent("OP",k -> k.length()),map.computeIfAbsent("OP",k -> k.length()));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfAbsentExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfAbsent("OP",k -> null),map.computeIfAbsent("OP",k -> null));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfAbsentNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfAbsent("MV",k -> k.length()),map.computeIfAbsent("MV",k -> k.length()));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfAbsentNotExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfAbsent("MV",k -> null),map.computeIfAbsent("MV",k -> null));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfPresentExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfPresent("OP",(k,v) -> k.length()*v),map.computeIfPresent("OP",(k,v) -> k.length()*v));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfPresentExistingKeyNullValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",null);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfPresent("OP",(k,v) -> k.length()*v),map.computeIfPresent("OP",(k,v) -> k.length()*v));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfPresentExistingKeyNullFunction()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfPresent("OP",(k,v) -> null),map.computeIfPresent("OP",(k,v) -> null));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfPresentNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfPresent("MV",(k,v) -> k.length()*v),map.computeIfPresent("MV",(k,v) -> k.length()*v));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeIfPresentNotExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.computeIfPresent("MV",(k,v) -> null),map.computeIfPresent("MV",(k,v) -> null));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.compute("OP",(k,v) -> k.length()*v),map.compute("OP",(k,v) -> k.length()*v));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.compute("OP",(k,v) -> null),map.compute("OP",(k,v) -> null));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.compute("MV",(k,v) -> (v==null) ? k.length() : k.length()*v),map.compute("MV",(k,v) -> (v==null) ? k.length() : k.length()*v));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testComputeNotExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.compute("MV",(k,v) -> null),map.compute("MV",(k,v) -> null));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testMergeExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.merge("OP",33,(v1,v2) -> v1-v2),map.merge("OP",33,(v1,v2) -> v1-v2));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testMergeExistingKeyNullValue()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",null);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.merge("OP",33,(v1,v2) -> v1-v2),map.merge("OP",33,(v1,v2) -> v1-v2));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testMergeExistingKeyNullFunction()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.merge("OP",33,(v1,v2) -> null),map.merge("OP",33,(v1,v2) -> null));
        assertEquals(oldMap.get("OP"),map.get("OP"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testMergeNotExistingKey()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.merge("MV",33,(v1,v2) -> v1-v2),map.merge("MV",33,(v1,v2) -> v1-v2));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testMergeNotExistingKeyNull()
    {
        java.util.HashMap<String, Integer> oldMap=new java.util.HashMap<String, Integer>();
        oldMap.put("OP",81);
        oldMap.put("LN",4);
        HashMap<String,Integer> map=new HashMap<>();
        map.putAll(oldMap);
        assertEquals(oldMap.merge("MV",33,(v1,v2) -> null),map.merge("MV",33,(v1,v2) -> null));
        assertEquals(oldMap.get("MV"),map.get("MV"));
        assertEquals(oldMap.size(),map.size());
    }

    @Test
    public void testReplaceAll()
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
        map.putAll(oldMap);
        oldMap.replaceAll((k,v) -> (v==null) ? k.length() : k.length()*v);
        map.replaceAll((k,v) -> (v==null) ? k.length() : k.length()*v);
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
    public void testConstructorExceptionCapacity()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(-5,0.75F));
    }

    @Test
    public void testConstructorExceptionLoadFactor()
    {
        assertThrows(IllegalArgumentException.class, () -> new HashMap<String, Integer>(3,-1.0F));
    }

    @Test
    public void testComputeIfAbsentException()
    {
        HashMap<String, Integer> map =new HashMap<String, Integer>();
        assertThrows(ArithmeticException.class, () -> map.computeIfAbsent("LS",(k) -> k.length()/0));
    }

    @Test
    public void testComputeIfPresentException()
    {
        HashMap<String, Integer> map =new HashMap<String, Integer>();
        map.put("LS",0);
        assertThrows(ArithmeticException.class, () -> map.computeIfPresent("LS",(k,v) -> k.length()/v));
    }

    @Test
    public void testComputeException()
    {
        HashMap<String, Integer> map =new HashMap<String, Integer>();
        assertThrows(NullPointerException.class, () -> map.compute("LS",(k,v) -> k.length()/v));
    }

    @Test
    public void testMergeException()
    {
        HashMap<String, Integer> map =new HashMap<String, Integer>();
        map.put("LS",0);
        assertThrows(ArithmeticException.class, () -> map.merge("LS",0,(v1,v2) -> v1/v2));
    }

}
