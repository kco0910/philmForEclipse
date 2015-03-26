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
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.philm.in.model.PhilmMovieCredit;
import app.philm.in.model.PhilmPerson;
import app.philm.in.state.MoviesState;

import com.uwetrottmann.tmdb.entities.CastMember;

@Singleton
public class TmdbCastEntityMapper extends BaseEntityMapper<CastMember, PhilmPerson> {

    @Inject
    public TmdbCastEntityMapper(MoviesState state) {
        super(state);
    }

    @Override
    public PhilmPerson map(CastMember entity) {
        PhilmPerson item = getEntity(String.valueOf(entity.id));

        if (item == null) {
            // No item, so create one
            item = new PhilmPerson();
        }

        // We already have a movie, so just update it wrapped value
        item.setFromTmdb(entity);
        putEntity(item);

        return item;
    }

    public List<PhilmMovieCredit> mapCredits(List<CastMember> entities) {
        final ArrayList<PhilmMovieCredit> credits = new ArrayList<PhilmMovieCredit>(entities.size());
        for (CastMember entity : entities) {
            credits.add(new PhilmMovieCredit(map(entity), entity.character, entity.order));
        }
        Collections.sort(credits);
        return credits;
    }

    @Override
    PhilmPerson getEntity(String id) {
        return mMoviesState.getPeople().get(id);
    }

    @Override
    void putEntity(PhilmPerson entity) {
        mMoviesState.getPeople().put(String.valueOf(entity.getTmdbId()), entity);
    }
}
