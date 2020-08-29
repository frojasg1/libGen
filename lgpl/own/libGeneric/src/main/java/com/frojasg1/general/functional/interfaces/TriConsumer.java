/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frojasg1.general.functional.interfaces;

/**
 * https://byexample.xyz/java/8/functionalinterfaces/#custom-functional-interfaces
 */
@FunctionalInterface
public interface TriConsumer<P1, P2, P3> {
    void accept(P1 p1, P2 p2, P3 p3);
}
