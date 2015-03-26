/*
 * Copyright 2014 Chris Banes
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

package app.philm.in.state.mappers;

import java.util.ArrayList;
import java.util.List;

import app.philm.in.state.MoviesState;

import com.google.common.base.Preconditions;

abstract class BaseEntityMapper<T, R> {
    final MoviesState mMoviesState;

    public BaseEntityMapper(MoviesState state) {
        mMoviesState = Preconditions.checkNotNull(state, "state cannot be null");
    }

    public abstract R map(T entity);

    public List<R> mapAll(List<T> entities) {
        final ArrayList<R> movies = new ArrayList<R>(entities.size());
        for (T entity : entities) {
            movies.add(map(entity));
        }
        return movies;
    }

    abstract R getEntity(String id);

    abstract void putEntity(R entity);
}
