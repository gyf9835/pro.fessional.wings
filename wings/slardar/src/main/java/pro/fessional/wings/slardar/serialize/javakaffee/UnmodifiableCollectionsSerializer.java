/*
 * Copyright 2010 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package pro.fessional.wings.slardar.serialize.javakaffee;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A kryo {@link Serializer} for unmodifiable {@link Collection}s and {@link Map}s
 * created via {@link Collections}.
 *
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class UnmodifiableCollectionsSerializer extends Serializer<Object> {

    private static final Field SOURCE_COLLECTION_FIELD;
    private static final Field SOURCE_MAP_FIELD;

    static {
        try {
            SOURCE_COLLECTION_FIELD = Class.forName("java.util.Collections$UnmodifiableCollection")
                                           .getDeclaredField("c");
            SOURCE_COLLECTION_FIELD.setAccessible(true);


            SOURCE_MAP_FIELD = Class.forName("java.util.Collections$UnmodifiableMap")
                                    .getDeclaredField("m");
            SOURCE_MAP_FIELD.setAccessible(true);
        }
        catch (final Exception e) {
            throw new RuntimeException("Could not access source collection" +
                                       " field in java.util.Collections$UnmodifiableCollection.", e);
        }
    }

    @Override
    public Object read(final Kryo kryo, final Input input, final Class<?> clazz) {
        final int ordinal = input.readInt(true);
        final UnmodifiableCollection unmodifiableCollection = UnmodifiableCollection.values()[ordinal];
        final Object sourceCollection = kryo.readClassAndObject(input);
        return unmodifiableCollection.create(sourceCollection);
    }

    @Override
    public void write(final Kryo kryo, final Output output, final Object object) {
        try {
            final UnmodifiableCollection unmodifiableCollection = UnmodifiableCollection.valueOfType(object.getClass());
            // the ordinal could be replaced by s.th. else (e.g. a explicitely managed "id")
            output.writeInt(unmodifiableCollection.ordinal(), true);
            kryo.writeClassAndObject(output, unmodifiableCollection.sourceCollectionField.get(object));
        }
        catch (final RuntimeException e) {
            // Don't eat and wrap RuntimeExceptions because the ObjectBuffer.write...
            // handles SerializationException specifically (resizing the buffer)...
            throw e;
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object copy(Kryo kryo, Object original) {
        try {
            final UnmodifiableCollection unmodifiableCollection = UnmodifiableCollection.valueOfType(original.getClass());
            Object sourceCollectionCopy = kryo.copy(unmodifiableCollection.sourceCollectionField.get(original));
            return unmodifiableCollection.create(sourceCollectionCopy);
        }
        catch (final RuntimeException e) {
            // Don't eat and wrap RuntimeExceptions
            throw e;
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private enum UnmodifiableCollection {
        COLLECTION(Collections.unmodifiableCollection(Arrays.asList("")).getClass(), SOURCE_COLLECTION_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableCollection((Collection<?>) sourceCollection);
            }
        },
        RANDOM_ACCESS_LIST(Collections.unmodifiableList(new ArrayList<Void>()).getClass(), SOURCE_COLLECTION_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableList((List<?>) sourceCollection);
            }
        },
        LIST(Collections.unmodifiableList(new LinkedList<Void>()).getClass(), SOURCE_COLLECTION_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableList((List<?>) sourceCollection);
            }
        },
        SET(Collections.unmodifiableSet(new HashSet<Void>()).getClass(), SOURCE_COLLECTION_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableSet((Set<?>) sourceCollection);
            }
        },
        SORTED_SET(Collections.unmodifiableSortedSet(new TreeSet<Void>()).getClass(), SOURCE_COLLECTION_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableSortedSet((SortedSet<?>) sourceCollection);
            }
        },
        MAP(Collections.unmodifiableMap(new HashMap<Void, Void>()).getClass(), SOURCE_MAP_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableMap((Map<?, ?>) sourceCollection);
            }

        },
        SORTED_MAP(Collections.unmodifiableSortedMap(new TreeMap<Void, Void>()).getClass(), SOURCE_MAP_FIELD) {
            @Override
            public Object create(final Object sourceCollection) {
                return Collections.unmodifiableSortedMap((SortedMap<?, ?>) sourceCollection);
            }
        };

        private final Class<?> type;
        private final Field sourceCollectionField;

        UnmodifiableCollection(final Class<?> type, final Field sourceCollectionField) {
            this.type = type;
            this.sourceCollectionField = sourceCollectionField;
        }

        /**
         * @param sourceCollection source
         */
        public abstract Object create(Object sourceCollection);

        static UnmodifiableCollection valueOfType(final Class<?> type) {
            for (final UnmodifiableCollection item : values()) {
                if (item.type.equals(type)) {
                    return item;
                }
            }
            throw new IllegalArgumentException("The type " + type + " is not supported.");
        }

    }

    /**
     * Creates a new {@link UnmodifiableCollectionsSerializer} and registers its serializer
     * for the several unmodifiable Collections that can be created via {@link Collections},
     * including {@link Map}s.
     *
     * @param kryo the {@link Kryo} instance to set the serializer on.
     * @see Collections#unmodifiableCollection(Collection)
     * @see Collections#unmodifiableList(List)
     * @see Collections#unmodifiableSet(Set)
     * @see Collections#unmodifiableSortedSet(SortedSet)
     * @see Collections#unmodifiableMap(Map)
     * @see Collections#unmodifiableSortedMap(SortedMap)
     */
    public static void registerSerializers(final Kryo kryo) {
        final UnmodifiableCollectionsSerializer serializer = new UnmodifiableCollectionsSerializer();
        UnmodifiableCollection.values();
        for (final UnmodifiableCollection item : UnmodifiableCollection.values()) {
            kryo.register(item.type, serializer);
        }
    }

}
