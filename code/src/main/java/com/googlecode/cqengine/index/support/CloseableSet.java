/**
 * Copyright 2012-2015 Niall Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.cqengine.index.support;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * Utility class to facilitate the addition of ResultSet's resources in the QueryOptions' CloseableQueryResources.
 *
 * @author Silvano Riz
 */
public class CloseableSet implements Closeable {

    final Set<Closeable> closeables = Collections.newSetFromMap(new IdentityHashMap<Closeable, Boolean>());

    public boolean add(Closeable closeable) {
        return closeables.add(closeable);
    }

    @Override
    public void close() throws IOException {
        for (Iterator<Closeable> iterator = closeables.iterator(); iterator.hasNext(); ) {
            Closeable closeable = iterator.next();
            CloseableQueryResources.closeQuietly(closeable);
            iterator.remove();
        }
    }

    @Override
    public String toString() {
        return closeables.toString();
    }
}
