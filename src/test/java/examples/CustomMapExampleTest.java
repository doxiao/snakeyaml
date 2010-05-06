/**
 * Copyright (c) 2008-2010, http://code.google.com/p/snakeyaml/
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

package examples;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class CustomMapExampleTest extends TestCase {
    @SuppressWarnings("unchecked")
    public void testMap() throws IOException {
        Loader loader = new Loader(new CustomConstructor());
        Yaml yaml = new Yaml(loader);
        Map data = (Map) yaml.load("{2: '222', 1: '111', 3: '333'}");
        assertTrue(data instanceof TreeMap);
        Object[] keys = data.keySet().toArray();
        // must be sorted
        assertEquals(new Integer(1), keys[0]);
        assertEquals(new Integer(2), keys[1]);
        assertEquals(new Integer(3), keys[2]);
    }

    class CustomConstructor extends Constructor {
        @Override
        protected Map<Object, Object> createDefaultMap() {
            return new TreeMap<Object, Object>();
        }
    }
}
