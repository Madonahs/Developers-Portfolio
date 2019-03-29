/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.data.actions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.madonasyombua.growwithgoogleteamproject.interfaces.Callback;
import com.madonasyombua.growwithgoogleteamproject.data.db.FirebaseObject;
import com.madonasyombua.growwithgoogleteamproject.data.models.Paths;
import java.util.Map;


/**
 * CRUD class for Firebase interactions
 */

@SuppressWarnings("unused")
public class FirebaseAction {

    public static final int ERROR_CODE_DATA_NULL = 0;
    private static final int ERROR_CODE_TOKEN_NULL = 1;
    public static final int ERROR_CODE_USER_NULL = 2;
    private static final String ERROR_DATA_NULL = "Error: DataSnapshot null.";
    private static final String ERROR_TOKEN_NULL = "Error: Firebase token null.";
    public static final String ERROR_USER_NULL = "Error: FirebaseAuth user null.";

    /**
     * Getter for root reference.
     * @return reference to database root
     */
    public static DatabaseReference getBase() {
        return FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Gets the users auth token
     * @param cb fired when token ready or on error
     */
    public static void getToken(final Callback<String> cb) {
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null) {
            u.getIdToken(true).addOnCompleteListener(task -> {
                if (task.getResult().getToken() != null) {
                    cb.onComplete(task.getResult().getToken());
                } else {
                    cb.onError(ERROR_CODE_TOKEN_NULL, ERROR_TOKEN_NULL);
                }
            });
        } else {
            cb.onError(ERROR_CODE_USER_NULL, ERROR_USER_NULL);
        }
    }

    /**
     * Writes an object to db
     * @param o firebase object
     * @param cl completion listener
     */
    public static void write(FirebaseObject o, CompletionListener cl) {
        if (o.hasKey()) {
            getBase().child(o.getPath()).setValue(o, cl);
        } else {
            getBase().child(o.getBase()).push().setValue(o, cl);
        }
    }

    /**
     * Updates a firebase object on db
     * @param o firebase object
     * @param cl completion listener
     */
    public static void update(FirebaseObject o, CompletionListener cl) {
        getBase().child(o.getPath()).setValue(o, cl);
    }

    /**
     * Delete an object
     * @param o firebase object
     * @param cl completion listener
     */
    public static void delete(FirebaseObject o, CompletionListener cl) {
        getBase().child(o.getPath()).setValue(null, cl);
    }

    /**
     * Get an object knowing its class
     * @param key the key to retrieve
     * @param cls the firebase object
     * @param callback fired when ready
     * @param <T> an implementation of FirebaseObject
     */
    public static <T extends FirebaseObject> void getByClass(String key, final Class<T> cls, final
    FirebaseCallback callback) {

        try {
            getBase().child(cls.getDeclaredConstructor(String.class)
                    .newInstance(key)
                    .getPath())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                T o = dataSnapshot.getValue(cls);
                                if (o != null) {
                                    o.setKey(dataSnapshot.getKey());
                                    callback.onComplete(o);
                                } else {
                                    callback.onError(ERROR_CODE_DATA_NULL, ERROR_DATA_NULL);
                                }
                            } else {
                                callback.onError(ERROR_CODE_DATA_NULL, ERROR_DATA_NULL);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callback.onError(databaseError.getCode(), databaseError.toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get an object knowing its path
     * @param path the path of the data
     * @param cls class of the data
     * @param gc completion listener
     * @param <T> object implementing FirebaseObject
     */
    public static <T extends FirebaseObject> void get(String path, final Class<T> cls, final FirebaseCallback<T> gc) {
        getBase().child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    T o = dataSnapshot.getValue(cls);
                    gc.onComplete(o);
                } else {
                    gc.onError(ERROR_CODE_DATA_NULL, ERROR_DATA_NULL);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                gc.onError(databaseError.getCode(), databaseError.getMessage());
            }
        });
    }

    /**
     * Update children of an object
     * @param path the path to the object
     * @param map the updated data
     * @param gc completion listener
     */
    public static void updateChildren(String path, Map<String, Object> map,
                                      final Callback<Boolean> gc) {
        DatabaseReference ref = getBase();

        if (!path.equals(Paths.SL)) {
            ref = ref.child(path);
        }

        ref.updateChildren(map, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                gc.onComplete(true);
            } else {
                gc.onError(databaseError.getCode(), databaseError.getMessage());
            }
        });
    }

    /**
     * Interface for implementing completion callbacks
     * @param <T> object implementing FirebaseObject
     */
    interface FirebaseCallback<T extends FirebaseObject> extends Callback<T> {

    }

}
