import {
  UserRound,
  Star,
  UsersRound,
  ShieldCheck
} from "lucide-react";

function Stats() {

  const stats = [
    {
      icon: <UserRound />,
      value: "10K+",
      label: "Happy Customers"
    },
    {
      icon: <Star />,
      value: "4.8",
      label: "Average Rating"
    },
    {
      icon: <UsersRound />,
      value: "50+",
      label: "Service Partners"
    },
    {
      icon: <ShieldCheck />,
      value: "100%",
      label: "Satisfaction Guarantee"
    }
  ];

  return (
    <section className="stats">

      {stats.map((stat, index) => (

        <div
          className="stat"
          key={stat.label}
        >

          <div className="stat-icon">
            {stat.icon}
          </div>

          <div>
            <strong>{stat.value}</strong>
            <p>{stat.label}</p>
          </div>

          {index !== stats.length - 1 && (
            <div className="stat-divider" />
          )}

        </div>

      ))}

    </section>
  );
}

export default Stats;