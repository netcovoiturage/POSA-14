package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore implementation using
 *        Java a ReentrantLock and a ConditionObject. It must implement both
 *        "Fair" and "NonFair" semaphore semantics, just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	private ReentrantLock rLock;

    /**
     * Define a ConditionObject to wait while the number of
     * permits is 0.
     */
    // TODO - you fill in here
	private Condition cObject;
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private final int permits;
	private int currentPermits;
	
    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	this.permits = permits;
    	currentPermits = permits;
    	rLock = new ReentrantLock(fair);
    	cObject = rLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
	public void acquire() throws InterruptedException {
		// TODO - you fill in here.
		try {
			rLock.lock();
			while (availablePermits() == 0) {
				cObject.await();
			}
			currentPermits--;
		} finally {
			rLock.unlock();
		}
	}

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
	public void acquireUninterruptibly() {
		// TODO - you fill in here.
		try {
			rLock.lock();
			while (availablePermits() == 0) {
				cObject.awaitUninterruptibly();
			}
			currentPermits--;
		} finally {
			rLock.unlock();
		}
	}

    /**
     * Return one permit to the semaphore.
     */
	void release() {
		// TODO - you fill in here.
		if (availablePermits() < permits) {
			try {
				rLock.lock();
				currentPermits++;
				cObject.signalAll();
			} finally {
				rLock.unlock();
			}
		}
	}

    /**
     * Return the number of permits available.
     */
	public int availablePermits() {
		// TODO - you fill in here by changing null to the appropriate
		// return value.
		int permits;
		try {
			rLock.lock();
			permits = currentPermits;
		} finally {
			rLock.unlock();
		}
		return permits;
	}
}
