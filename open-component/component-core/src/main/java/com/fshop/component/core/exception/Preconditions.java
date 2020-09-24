/*
 * Copyright 2020 rannuo1010@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fshop.component.core.exception;

/**
 * Description: 前提条件验证（不满足则抛出去异常）
 *
 * @author 然诺
 * @date 2020/2/22
 */
public final class Preconditions {

	private Preconditions() {
	}

	public static <T> void checkNull(T reference, IExceptionCode exCode) {
		if (reference != null) {
			throw new BizException(exCode);
		}
	}

	public static <T> T checkNotNull(T reference, IExceptionCode exCode) {
		if (reference == null) {
			throw new BizException(exCode);
		} else {
			return reference;
		}
	}

	public static void checkTrue(boolean expression, IExceptionCode exCode) {
		if (!expression) {
			throw new BizException(exCode);
		}
	}

	public static void checkFalse(boolean expression, IExceptionCode exCode) {
		if (expression) {
			throw new BizException(exCode);
		}
	}
}
