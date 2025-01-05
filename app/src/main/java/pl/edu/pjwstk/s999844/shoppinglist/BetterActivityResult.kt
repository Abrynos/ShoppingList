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
 * Copyright (C) 2021-2025 Sebastian Göls
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

package pl.edu.pjwstk.s999844.shoppinglist

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts


class BetterActivityResult<Input, Result> private constructor(caller: ActivityResultCaller, contract: ActivityResultContract<Input, Result>, private var onActivityResult: OnActivityResult<Result>?) {
	fun interface OnActivityResult<O> {
		fun onActivityResult(result: O)
	}

	private val launcher: ActivityResultLauncher<Input>

	init {
		launcher = caller.registerForActivityResult(contract) { result: Result -> callOnActivityResult(result) }
	}

	fun setOnActivityResult(onActivityResult: OnActivityResult<Result>?) {
		this.onActivityResult = onActivityResult
	}


	fun launch(input: Input, onActivityResult: OnActivityResult<Result>? = this.onActivityResult) {
		if (onActivityResult != null) {
			this.onActivityResult = onActivityResult
		}
		launcher.launch(input)
	}

	private fun callOnActivityResult(result: Result) {
		if (onActivityResult != null) {
			onActivityResult!!.onActivityResult(result)
		}
	}

	companion object {
		fun <Input, Result> registerForActivityResult(caller: ActivityResultCaller, contract: ActivityResultContract<Input, Result>, onActivityResult: OnActivityResult<Result>?): BetterActivityResult<Input, Result> {
			return BetterActivityResult(caller, contract, onActivityResult)
		}

		fun <Input, Result> registerForActivityResult(caller: ActivityResultCaller, contract: ActivityResultContract<Input, Result>): BetterActivityResult<Input, Result> {
			return registerForActivityResult(caller, contract, null)
		}

		fun registerActivityForResult(caller: ActivityResultCaller): BetterActivityResult<Intent, ActivityResult> {
			return registerForActivityResult(caller, ActivityResultContracts.StartActivityForResult())
		}
	}
}
