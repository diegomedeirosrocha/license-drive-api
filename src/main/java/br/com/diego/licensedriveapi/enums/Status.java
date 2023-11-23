package br.com.diego.licensedriveapi.enums;

public enum Status {

    ALLOW,
    DENY,
    ALLOW_IF_PERMISSION_FATHER;

    public static boolean isValid( String statusReceive ) {
        for( final Status status : Status.values() ) {
            if( status.toString().equalsIgnoreCase( statusReceive ) ) {
                return true;
            }
        }

        throw new IllegalArgumentException(statusReceive);
    }

}
