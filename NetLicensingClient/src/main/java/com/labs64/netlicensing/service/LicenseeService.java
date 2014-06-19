package com.labs64.netlicensing.service;

import com.labs64.netlicensing.domain.entity.Licensee;
import com.labs64.netlicensing.domain.vo.Page;
import com.labs64.netlicensing.domain.entity.ValidationResult;
import com.labs64.netlicensing.exception.BaseCheckedException;
import com.labs64.netlicensing.domain.vo.Context;

/**
 * Provides licensee handling routines.
 * <p/>
 * Licensee is usually an end customer, capable of obtaining licenses for the product. Each licensee is associated with
 * a single {@linkplain ProductService product}. From the vendor perspective a licensee object in NetLicensing may
 * correspond to a physical instance of the product, customer account within a vendor's service, or it can represent a
 * security dongle given to a user. In practice, each licensee must only have a unique identifier associated with it,
 * that is communicated to NetLicensing as licensee number for performing operations related to this licensee. Licensee
 * doesn't need to have an own account within NetLicensing. There are two main operations performed for licensees:
 * validation and obtaining new licenses. Validation process is typically completely transparent to the end user and
 * performed from the vendor's product by means of this API. Licensee can be offered to obtain new licenses for the
 * product by redirecting the user to the NetLicensing Shop in the web browser.
 */
public class LicenseeService {

    /**
     * Creates new licensee object with given properties.
     *
     * @param context       determines the vendor on whose behalf the call is performed
     * @param productNumber parent product to which the new licensee is to be added
     * @param newLicensee   non-null properties will be taken for the new object, null properties will either stay null, or will
     *                      be set to a default value, depending on property.
     * @return the newly created licensee object
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static Licensee create(Context context, String productNumber, Licensee newLicensee) throws BaseCheckedException {
        return null;  // TODO: implement me...
    }

    /**
     * Gets licensee by its number.
     *
     * @param context determines the vendor on whose behalf the call is performed
     * @param number  the licensee number
     * @return the licensee
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static Licensee get(Context context, String number) throws BaseCheckedException {
        return null;  // TODO: implement me...
    }

    /**
     * Returns all licensees of a vendor.
     *
     * @param context determines the vendor on whose behalf the call is performed
     * @param filter  reserved for the future use, must be omitted / set to NULL
     * @return list of licensees (of all products) or null/empty list if nothing found.
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static Page<Licensee> list(Context context, String filter) throws BaseCheckedException {
        return null;  // TODO: implement me...
    }

    /**
     * Updates licensee properties.
     *
     * @param context        determines the vendor on whose behalf the call is performed
     * @param number         licensee number
     * @param updateLicensee non-null properties will be updated to the provided values, null properties will stay unchanged.
     * @return updated licensee.
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static Licensee update(Context context, String number, Licensee updateLicensee) throws BaseCheckedException {
        return null;  // TODO: implement me...
    }

    /**
     * Deletes licensee.
     *
     * @param context      determines the vendor on whose behalf the call is performed
     * @param number       licensee number
     * @param forceCascade if true, any entities that depend on the one being deleted will be deleted too
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static void delete(Context context, String number, boolean forceCascade) throws BaseCheckedException {
        // TODO: implement me...
    }

    /**
     * Validates active licenses of the licensee.
     *
     * @param context       determines the vendor on whose behalf the call is performed
     * @param number        licensee number
     * @param productNumber optional productNumber, must be provided in case licensee auto-create is enabled.
     * @throws BaseCheckedException any subclass of {@linkplain BaseCheckedException}. These exceptions will be transformed to the
     *                              corresponding service response messages.
     */
    public static ValidationResult validate(Context context, String number, String productNumber) throws BaseCheckedException {
        return null;  // TODO: implement me...
    }

}