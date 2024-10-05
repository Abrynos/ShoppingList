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
 * Copyright (C) 2021-2024 Sebastian Göls
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

import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.jetbrains.kotlin.android)
	alias(libs.plugins.ksp)
	alias(libs.plugins.compose)
}

kotlin {
	jvmToolchain(17)
}

android {
	namespace = "pl.edu.pjwstk.s999844.shoppinglist"
	compileSdk = VersionCodes.UPSIDE_DOWN_CAKE

	defaultConfig {
		applicationId = "pl.edu.pjwstk.s999844.shoppinglist"
		minSdk = VersionCodes.N_MR1
		targetSdk = VersionCodes.UPSIDE_DOWN_CAKE
		versionCode = 19
		versionName = "v1.17.0"

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
	lint {
		abortOnError = true
		warningsAsErrors = true
		checkAllWarnings = true
		showAll = true
		// sometimes we use the same string for similar but non-equal purposes
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
		// SDK 35 is not stable yet - August 2024
		disable.add("OldTargetApi")
	}
}

dependencies {
	implementation(libs.androidx.lifecycle.runtime)

	implementation(libs.androidx.navigation.fragment)
	implementation(libs.androidx.navigation.ui)

	implementation(libs.room.runtime)
	annotationProcessor(libs.room.compiler)
	ksp(libs.room.compiler)

	implementation(libs.androidx.core)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.material3)
	implementation(libs.androidx.constraintlayout)
}
