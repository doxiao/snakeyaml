/**
 * Copyright (c) 2008-2010 Andrey Somov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yaml.snakeyaml.nodes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.error.YAMLException;

public final class Tag {
    public static final String PREFIX = "tag:yaml.org,2002:";
    public static final Tag YAML = new Tag(PREFIX + "yaml");
    public static final Tag VALUE = new Tag(PREFIX + "value");
    public static final Tag MERGE = new Tag(PREFIX + "merge");
    public static final Tag SET = new Tag(PREFIX + "set");
    public static final Tag PAIRS = new Tag(PREFIX + "pairs");
    public static final Tag OMAP = new Tag(PREFIX + "omap");
    public static final Tag BINARY = new Tag(PREFIX + "binary");
    public static final Tag INT = new Tag(PREFIX + "int");
    public static final Tag FLOAT = new Tag(PREFIX + "float");
    public static final Tag TIMESTAMP = new Tag(PREFIX + "timestamp");
    public static final Tag BOOL = new Tag(PREFIX + "bool");
    public static final Tag NULL = new Tag(PREFIX + "null");
    public static final Tag STR = new Tag(PREFIX + "str");
    public static final Tag SEQ = new Tag(PREFIX + "seq");
    public static final Tag MAP = new Tag(PREFIX + "map");
    public static final Map<Tag, Set<Class<?>>> COMPATIBILITY_MAP;
    static {
        COMPATIBILITY_MAP = new HashMap<Tag, Set<Class<?>>>();
        Set<Class<?>> floatSet = new HashSet<Class<?>>();
        floatSet.add(Double.class);
        floatSet.add(Float.class);
        floatSet.add(BigDecimal.class);
        COMPATIBILITY_MAP.put(FLOAT, floatSet);
        //
        Set<Class<?>> intSet = new HashSet<Class<?>>();
        intSet.add(Integer.class);
        intSet.add(Long.class);
        intSet.add(BigInteger.class);
        COMPATIBILITY_MAP.put(INT, intSet);
        //
        Set<Class<?>> timestampSet = new HashSet<Class<?>>();
        timestampSet.add(Date.class);
        timestampSet.add(java.sql.Date.class);
        timestampSet.add(Timestamp.class);
        COMPATIBILITY_MAP.put(TIMESTAMP, timestampSet);
    }

    private final String value;

    private Tag(String tag) {
        this.value = tag;
    }

    public static Tag createTag(String tag) {
        if (tag == null) {
            return null;
        } else {
            return new Tag(tag);
        }
    }

    public Tag(Class<? extends Object> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class for tag must be provided.");
        }
        this.value = Tag.PREFIX + clazz.getName();
    }

    public String getValue() {
        return value;
    }

    public boolean startsWith(String prefix) {
        return value.startsWith(prefix);
    }

    public String getClassName() {
        if (!value.startsWith(Tag.PREFIX)) {
            throw new YAMLException("Unknown tag: " + value);
        }
        return value.substring(Tag.PREFIX.length());
    }

    public int getLength() {
        return value.length();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Tag) {
            return value.equals(((Tag) obj).getValue());
        } else if (obj instanceof String) {
            if (value.equals(obj.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Java has more then 1 one class compatible with a language-independent tag
     * 
     * @param clazz
     *            - Class to check compatibility
     * @return true when the Class can be represented by this
     *         language-independent tag
     */
    public boolean isCompatible(Class<?> clazz) {
        Set<Class<?>> set = COMPATIBILITY_MAP.get(Tag.createTag(value));
        if (set != null) {
            return set.contains(clazz);
        } else {
            return false;
        }
    }

    /**
     * Check whether this tag matches the global tag for the Class
     * 
     * @param clazz
     *            - Class to check
     * @return true when the this tag can be used as a global tag for the Class
     */
    public boolean matches(Class<? extends Object> clazz) {
        return value.equals(Tag.PREFIX + clazz.getName());
    }

}
