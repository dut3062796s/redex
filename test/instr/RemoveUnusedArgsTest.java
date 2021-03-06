/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.facebook.redex.test.instr;

class Foo {
  private int x;

  // One removable arg
  public Foo(int x) {
    this.x = 4;
  }

  // No removable args
  public Foo(int x, int y) {
    this.x = x + y;
  }

  // One colliding removable arg
  public Foo(int x, int y, double z) {
    this.x = x + y + 3;
  }
}

class FooUser {
  private int x;

  public void use_foo1() {
    Foo foo = new Foo(x);
  }

  public void use_foo2() {
    Foo foo = new Foo(1, 1);
  }

  public void use_foo3() {
    double d = 5.5;
    Foo foo = new Foo(1, 1, d);
  }
}

class Statics {
  public static void static1() {
    String hello = "I don't have arguments!";
  }

  public static void static2(boolean truthy) {
    String bonjour = "J'ai un argument";
    if (truthy) {
      bonjour = "Je use the argument";
    }
  }

  public static void static3(Object obj, double wide_param) {
    String ciao = "Je ne sais pas Italian; two arguments";
    wide_param *= 2.0;
    ciao = "wide_param is alive, obj is dead";
  }
}

class StaticsUser {
  public void use_static1() {
    Statics.static1();
  }

  public void use_static2() {
    Statics.static2(true);
  }

  public void use_static3(Foo foo) {
    double dub = 4.0;
    Statics.static3(foo, dub);
  }
}

class Privates {
  public void use_private_first() {
    private1(1.0, 2.0);
  }

  public void use_private_second() {
    private1(0, 1.0, 2.0);
  }

  // first
  private void private1(double x, double y) {
    x = x + y;
  }

  // second
  private void private1(int c, double x, double y) {
    x = x * y;
  }
}

class Parent {
}

class NonVirtuals extends Parent {
  public void non_virtual1(int x) {
    x = 5;
  }

  protected void non_virtual2(double x) {
    x = 5.0;
  }
}

class NonVirtualsUser {
  public void use_non_virtual1(NonVirtuals nv) {
    nv.non_virtual1(1);
  }

  public void use_non_virtual2(NonVirtuals nv) {
    nv.non_virtual2(1.0);
  }
}
