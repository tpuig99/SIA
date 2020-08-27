package Constants;

public enum Role {
        WARRIOR("warrior",0.6,0.6),
        ARCHER("archer",0.9,0.1),
        DEFENDER("defender",0.3,0.8),
        INFILTRATE("infiltrate",0.8,0.3);
        private final String rol;
        private final double attackId;
        private final double defenseId;

        Role(String rol, double attackId, double defenseId) {
            this.rol = rol;
            this.attackId = attackId;
            this.defenseId = defenseId;
        }

        public String getRol() {
            return rol;
        }

        public double getAttackId() {
            return attackId;
        }

        public double getDefenseId() {
            return defenseId;
        }

}
