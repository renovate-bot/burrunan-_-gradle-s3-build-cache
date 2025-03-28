/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.burrunan.s3cache

import org.gradle.api.Action
import org.gradle.caching.configuration.AbstractBuildCache
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.services.s3.S3ClientBuilder
import software.amazon.awssdk.services.s3.S3Configuration

open class AwsS3BuildCache : AbstractBuildCache() {
    var region: String? = null
    var bucket: String? = null
    var prefix: String? = "cache/"
    var kmsKeyId: String? = null
    var maximumCachedObjectLength: Long = 50 * 1024 * 1024
    var isReducedRedundancy = true
    var endpoint: String? = null
    var forcePathStyle: Boolean = false
    var headers: Map<String?, String?>? = null
    var awsAccessKeyId: String? = null
        get() = field ?: System.getenv("S3_BUILD_CACHE_ACCESS_KEY_ID")
    var awsSecretKey: String? = null
        get() = field ?: System.getenv("S3_BUILD_CACHE_SECRET_KEY")
    var sessionToken: String? = null
        get() = field ?: System.getenv("S3_BUILD_CACHE_SESSION_TOKEN")
    var awsProfile: String? = null
        get() = field ?: System.getenv("S3_BUILD_CACHE_PROFILE")

    // OIDC Configuration
    var awsWebIdentityTokenFile: String? = null
        get() = field ?: System.getenv("AWS_WEB_IDENTITY_TOKEN_FILE")
    var awsRoleARN: String? = null
        get() = field ?: System.getenv("AWS_ROLE_ARN")

    var lookupDefaultAwsCredentials: Boolean = false
    var credentialsProvider: AwsCredentialsProvider? = null
    var showStatistics: Boolean = true
    var showStatisticsWhenImpactExceeds: Long = 100
    var showStatisticsWhenSavingsExceeds: Long = 100
    var showStatisticsWhenWasteExceeds: Long = 100
    var showStatisticsWhenTransferExceeds: Long = 10 * 1024 * 1024
    var transferAcceleration: Boolean? = false
    internal val s3ClientActions = mutableListOf<Action<S3ClientBuilder>>()
    internal val s3ConfigurationActions = mutableListOf<Action<S3Configuration.Builder>>()

    fun s3client(action: Action<S3ClientBuilder>) {
        s3ClientActions += action
    }

    fun s3configuration(action: Action<S3Configuration.Builder>) {
        s3ConfigurationActions += action
    }
}
