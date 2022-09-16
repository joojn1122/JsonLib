package com.joojn.json.util;

import java.util.*;

public class ProtectedMap<K,V>  {

    private final List<EntrySet<K, V>> map;
    private final int mods;

    public static int CASE_INSENSITIVE = 1;
    public static int MEMORY_COMPARE = 2;

    private boolean getMod(int mod)
    {
        return (this.mods & mod) == mod;
    }

    private boolean objectEquals(K key1, K key2)
    {
        if(getMod(MEMORY_COMPARE))
        {
            return key1 == key2;
        }

        if(getMod(CASE_INSENSITIVE) && key1 instanceof String && key2 instanceof String)
        {
            return ((String) key1).equalsIgnoreCase((String) key2);
        }

        return key1.equals(key2);
    }

    public ProtectedMap()
    {
        this(0);
    }

    public ProtectedMap(int mods)
    {
        this.map = new ArrayList<>();
        this.mods = mods;
    }

    protected void clear_()
    {
        this.map.clear();
    }

    public int size()
    {
        return this.map.size();
    }

    protected void put_(K key, V value)
    {
        EntrySet<K, V> entry = this.getEntry(key);

        if(entry == null)
        {
            map.add(new EntrySet<>(key, value));
        }
        else
        {
            entry.setValue(value);
        }
    }

    protected V get_(K key)
    {
        EntrySet<K, V> entry = getEntry(key);

        return entry == null ? null : entry.getValue();
    }

    private EntrySet<K, V> getEntry(K key)
    {
        for(EntrySet<K, V> entry : map)
        {
            if((objectEquals(entry.getKey(), key))
            )
            {
                return entry;
            }
        }

        return null;
    }

    protected boolean containsKey_(K key)
    {
        return getEntry(key) != null;
    }

    protected boolean remove_(K key)
    {
        for(int i = 0; i < map.size() ; i++)
        {
            EntrySet<K, V> entry = map.get(i);

            if(objectEquals(entry.getKey(), key))
            {
                map.remove(i);
                return true;
            }
        }

        return false;
    }

    public List<EntrySet<K, V>> getEntrySet()
    {
        return this.map;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("{");

        for(int i = 0; i < this.map.size() ; i++)
        {
            EntrySet<K, V> entry = this.map.get(i);

            builder.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append((i != this.map.size() - 1) ? ", " : "");
        }

        return builder + "}";
    }


}
