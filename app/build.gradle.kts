/*
 *  _____ _                       _             _     _     _
 * /  ___| |                     (_)           | |   (_)   | |
 * \ `--.| |__   ___  _ __  _ __  _ _ __   __ _| |    _ ___| |_
 *  `--. \ '_ \ / _ \| '_ \| '_ \| | '_ \ / _` | |   | / __| __|
 * /\__/ / | | | (_) | |_) | |_) | | | | | (_| | |___| \__ \ |_
 * \____/|_| |_|\___/| .__/| .__/|_|_| |_|\__, \_____/_|___/\__|
 *                   | |   | |             __/ |
 *                   |_|   |_|            |___/
 *
 * Copyright (C) 2021-2022 Sebastian Göls
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License only
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("com.google.devtools.ksp")
}

android {
	namespace = "pl.edu.pjwstk.s999844.shoppinglist"
	compileSdk = 34

	defaultConfig {
		applicationId = "pl.edu.pjwstk.s999844.shoppinglist"
		minSdk = 25
		targetSdk = 34
		versionCode = 17
		versionName = "v1.15.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		vectorDrawables {
			useSupportLibrary = true
		}
		javaCompileOptions {
			annotationProcessorOptions {
				argument("room.incremental", "true")
				argument("room.schemaLocation", "$projectDir/schemas")
			}
		}
	}
	applicationVariants.all {
		resValue("string", "versionName", versionName)
	}
	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		compose = true
		viewBinding = true
		dataBinding = true
	}
	dataBinding.enable = true
	viewBinding.enable = true
	composeOptions {
		kotlinCompilerExtensionVersion = "1.4.3"
	}
	lint {
		abortOnError = true
		warningsAsErrors = true
		checkAllWarnings = true
		showAll = true
		htmlReport = true
	}
}

dependencies {
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

	val androidxNavigationVersion = "2.7.3"
	implementation("androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion")
	implementation("androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion")

	val roomVersion = "2.5.2"
	implementation("androidx.room:room-runtime:$roomVersion")
	annotationProcessor("androidx.room:room-compiler:$roomVersion")
	ksp("androidx.room:room-compiler:$roomVersion")

	implementation("androidx.core:core-ktx:1.12.0")
	implementation("androidx.appcompat:appcompat:1.6.1")
	implementation("com.google.android.material:material:1.9.0")
	// TODO - migrate to material3
	// implementation("androidx.compose.material3:material3")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	implementation("androidx.activity:activity-compose:1.7.2")
	implementation(platform("androidx.compose:compose-bom:2023.03.00"))
	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.1.5")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
