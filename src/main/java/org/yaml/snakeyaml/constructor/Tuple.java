/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml.constructor;

public class Tuple<T, K> {

    private final T _1;
    private final K _2;

    public Tuple(T _1, K _2) {
        if (_1 == null || _2 == null) {
            throw new NullPointerException("Tuple does not accept NULL.");
        }
        this._1 = _1;
        this._2 = _2;
    }

    public K _2() {
        return _2;
    }

    public T _1() {
        return _1;
    }
}