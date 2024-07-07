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
 * Copyright (C) 2021-2023 Sebastian Göls
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
	id("com.google.devtools.ksp")
}

kotlin {
	jvmToolchain(17)
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
	}
	applicationVariants.all {
		resValue("string", "versionName", versionName)
	}
	buildTypes {
		release {
			isMinifyEnabled = true
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
			signingConfig = signingConfigs.getByName("debug")
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
	}
	dataBinding.enable = true
	viewBinding.enable = true
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.14"
	}
	lint {
		abortOnError = true
		warningsAsErrors = true
		checkAllWarnings = true
		showAll = true
		// sometimes we use the same string for similuar but non-equal purposes
		disable.add("DuplicateStrings")
		// This is an open source project and we don't have a person of each language sitting around translating stuff for us
		disable.add("MissingTranslation")
		// the launcher icons are the only ones affected by this and they have transparency in them - no point in converting
		disable.add("ConvertToWebp")
		// easier to read if in xml and not in a bunch of lambdas within the setup method for the activity
		disable.add("UsingOnClickInXml")
		// two library updates in parallel will make both CI builds fail and cause dependabot to not merge either, requiring manual work
		disable.add("GradleDependency")
		// using view binding will result in these
		disable.add("UnusedIds")
		// I'm not a french person so I have no idea what is correct here - let the translators take care of this
		disable.add("TypographyQuotes")
	}
}

dependencies {
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")

	val androidxNavigationVersion = "2.7.7"
	implementation("androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion")
	implementation("androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion")

	val roomVersion = "2.6.1"
	implementation("androidx.room:room-runtime:$roomVersion")
	annotationProcessor("androidx.room:room-compiler:$roomVersion")
	ksp("androidx.room:room-compiler:$roomVersion")

	implementation("androidx.core:core-ktx:1.13.1")
	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("androidx.compose.material3:material3:1.2.1")
	implementation("androidx.constraintlayout:constraintlayout:2.1.4")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
	androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
