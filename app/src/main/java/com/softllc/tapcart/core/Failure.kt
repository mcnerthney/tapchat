package com.softllc.tapcart.core

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}