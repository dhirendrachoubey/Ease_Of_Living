package in.nic.ease_of_living.dbo;

import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neha on 8/9/2017.
 */

public class InputFilters {

    /* Functionality for filter to allow only alpha characters (A-Z a-z)*/
    public static InputFilter alphaFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };

    /* Functionality for filter to allow only alphanumeric characters (A-Z a-z, 0-9)*/
    public static InputFilter alphaNumericFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };

    /* Functionality for filter to allow only digits characters (0-9)*/
    public static InputFilter digitsFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[0-9]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };

    /* Functionality for filter to not allow space*/
    public static InputFilter noSpaceFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                Character char_curr = source.charAt(0);
                if (Character.isWhitespace(char_curr)) {
                    return "";
                }
            } catch (Exception e) {

            }
            return null;
        }
    };


    /* Functionality for filter to allow only alpha characters (A-Z a-z, space, .)*/
    public static InputFilter alphaWithSpSymbolFilter1 = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z0-9. ]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };




    /* Functionality for filter to allow alphanumeric, "-", "/"*/
    public static InputFilter alphaNumericWithSpCharFilter1 = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z0-9-/]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };

    /* Functionality for filter to allow alphanumeric, "-/ ,."*/
    public static InputFilter alphaNumericWithSpCharFilter2 = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z0-9-/ ,.]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };


    /* Functionality for password filter #, ?, !, @, $, %, ^, &amp; , *, -*/
    public static InputFilter passwordFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("^[a-zA-Z0-9#?!@$%^&*-]+$");
                Matcher ms;
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    ms = ps.matcher(String.valueOf(c));
                    if (ms.matches()) // put your condition here
                        sb.append(c);
                    else
                        keepOriginal = false;
                }
                if (keepOriginal)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }

            } catch (Exception e) {

            }
            return null;
        }
    };


    /* Functionality for filter to allow alphanumeric, "-/ ,."*/
    public static InputFilter decimalDigitFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                boolean keepOriginal = true;
                int digitsBeforeZero = 5;
                int digitsAfterZero = 2;
                StringBuilder sb = new StringBuilder(end - start);

                Pattern ps = Pattern.compile("[0-9]{0," + (digitsBeforeZero) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");

                Matcher ms;
                String s = Html.toHtml(dest).replaceAll("\\<.*?>", "").replaceAll("\n", "");
                Matcher matcher = ps.matcher(dest);
                if (!matcher.matches())
                    return "";

                if (Double.parseDouble(s) < 99999.99 && s.contains(".")) {
                    return null;
                } else if ((Double.parseDouble(s) < 10000 && !s.contains(".")) || source.equals(".")) {
                    return null;
                } else {
                    return "";
                }

            } catch (Exception e) {

            }
            return null;
        }
    };


}
