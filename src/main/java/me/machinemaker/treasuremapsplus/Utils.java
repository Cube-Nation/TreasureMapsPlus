/*
 * GNU General Public License v3
 *
 * TreasureMapsPlus, a plugin to alter treasure maps
 *
 * Copyright (C) 2023 Machine-Maker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.machinemaker.treasuremapsplus;

public final class Utils {


    private Utils() {
    }

    public static <T, E extends Throwable> T sneaky(final CheckedSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface CheckedSupplier<T, E extends Throwable> {

        T get() throws E;
    }
}
