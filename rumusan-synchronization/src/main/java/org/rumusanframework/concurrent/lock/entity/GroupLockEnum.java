package org.rumusanframework.concurrent.lock.entity;

/**
 * 
 * @author Harvan Irsyadi
 * @version 1.0.0
 *
 */
public enum GroupLockEnum {
    A(1L), B(2L), C(3L), D(4L), E(5L), F(6L), G(7L), H(8L), I(9L), J(10L), K(11L), L(12L), M(13L), N(14L), O(15L), P(
	    16L), Q(17L), R(18L), S(19L), T(20L), U(21L), V(22L), W(23L), X(24L), Y(25L), Z(26L);

    private final Long id;

    private GroupLockEnum(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }
}