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
package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.persistence.ExternalPersistence;

import java.sql.Connection;

/**
 * An {@link ExternalPersistence} which persists to a SQLite database.
 *
 * @author Silvano Riz
 */
public interface SQLitePersistence<O, A extends Comparable<A>> extends ExternalPersistence<O, A> {

    /**
     * Returns a {@link Connection} to the SQLite database used for persistence.
     *
     * @param index The {@link Index} requesting the connection.
     * @return The {@link Connection}
     */
    Connection getConnection(Index<?> index);

    /**
     * @return The number of bytes used to persist the collection and/or indexes.
     */
    long getBytesUsed();

    /**
     * Compacts the underlying persistence, which returns unused memory or disk space to the operating system.
     */
    void compact();

    /**
     * Expands the underlying persistence by the given number of bytes in a single operation. This will usually result
     * in the persistence being expanded into an additional contiguous chunk of memory or region on disk.
     * <p/>
     * After this method returns, the operating system will report that the memory or disk space used for persistence
     * has increased by this amount, but internally the space will simply be reserved for future use. The reserved space
     * will be used to store objects added to the collection subsequently, without needing to request more memory from
     * the OS ad-hoc.
     * <p/>
     * This can reduce fragmentation of the persistence file on some OS filesystems, especially if used prior to bulk
     * imports when the persistence is on a non-SSD disk.
     */
    void expand(long numBytes);

    /**
     * Creates a {@link SQLiteIdentityIndex} which persists objects in this persistence.
     * @return a {@link SQLiteIdentityIndex} which persists objects in this persistence.
     */
    SQLiteIdentityIndex<A, O> createIdentityIndex();

}
